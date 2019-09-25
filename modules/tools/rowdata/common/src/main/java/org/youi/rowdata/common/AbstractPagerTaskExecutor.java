/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.rowdata.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class AbstractPagerTaskExecutor<T> extends ThreadPoolTaskExecutor {

    private final static int DEFAULT_CORE_POOL_SIZE = 10;//默认10线程执行

    private final static int DEFAULT_RESULT_MESSAGE_CAPACITY = 1000;//异常记录最大存储条数

    private final static String STATUS_COMPLETE = "1";//执行完成的标识

    //存储执行结果
    private Map<String, PagerTaskResult> pagerTaskResults = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void initialize() {
        super.initialize();
        this.setCorePoolSize(DEFAULT_CORE_POOL_SIZE);
    }

    /**
     * 分页执行工具方法
     *
     * @param pager
     * @param pagerExecutor
     * @param pagerRecordsExecutor
     * @return
     */
    public PagerTaskResult execute(Pager pager,
                                   Function<Pager, PagerRecords> pagerExecutor,
                                   Function<PagerRecords, List<T>> pagerRecordsExecutor,
                                   Function<List<T>, String> resultCallback) {
        String instanceId = UUID.randomUUID().toString();
        PagerTaskResult pagerTaskResult = new PagerTaskResult();
        pagerTaskResult.setId(instanceId);//
        //
        pagerTaskResults.put(instanceId, pagerTaskResult);
        //TODO 优化线程执行
        //异步执行,启动一个新的线程执行耗时操作
        new Thread(() -> {
            List<T> resultMessages = new ArrayList<>();//存储前1000条错误信息
            //通过第一次查询获取总记录条数
            long totalCount = firstPageExecute(resultMessages, pager, pagerExecutor, pagerRecordsExecutor);
            Double pageSize = new Double(pager.getPageSize());//获取分页信息
            long pageCount = new Double(Math.ceil(totalCount / pageSize)).longValue();//计算总页数
            //
            pagerTaskResult.setTotalCount(totalCount);
            pagerTaskResult.setPageCount(pageCount);
            pagerTaskResult.increaseExecCount();
            //
            if (pageCount > 1) {
                pager.setPageType(Pager.QUERY_TYPE_LIST);
                executeNextAllPages(resultMessages, pager, totalCount, pagerExecutor, pagerRecordsExecutor, pagerTaskResult);
            }
            //
            resultCallback.apply(resultMessages);
            //执行完成
            pagerTaskResult.setStatus(STATUS_COMPLETE);
            pagerTaskResults.remove(instanceId);
        }).start();

        //返回记录总数
        return pagerTaskResult;
    }

    /**
     * 查看任务执行情况
     * @param instanceId
     * @return
     */
    public PagerTaskResult getInstancePagerTaskResult(String instanceId){
        PagerTaskResult pagerTaskResult;
        if(pagerTaskResults.containsKey(instanceId)){
            pagerTaskResult = pagerTaskResults.get(instanceId);
            if(STATUS_COMPLETE.equals(pagerTaskResult.getStatus())){
                pagerTaskResults.remove(instanceId);
            }
        }else{
            pagerTaskResult = new PagerTaskResult();
            pagerTaskResult.setId(instanceId);
            pagerTaskResult.setStatus(STATUS_COMPLETE);
        }
        return pagerTaskResult;
    }

    /**
     * 数据量较大时，异步执行数据
     *
     * @param resultMessages
     * @param pager
     * @param totalCount
     * @param pagerExecutor
     * @param pagerRecordsExecutor
     */
    private void executeNextAllPages(List<T> resultMessages, Pager pager, long totalCount, Function<Pager, PagerRecords> pagerExecutor,
                                     Function<PagerRecords, List<T>> pagerRecordsExecutor,
                                     PagerTaskResult pagerTaskResult) {
        long startTime = System.currentTimeMillis();
        List<Future<List<T>>> pagerTasks = new ArrayList<>();//任务执行结果集合
        Double pageSize = new Double(pager.getPageSize());//获取分页信息
        long pageCount = new Double(Math.ceil(totalCount / pageSize)).longValue();//计算总页数
        logger.info(MessageFormat.format("总共处理{0}行数据，每页{1}条，共{2}页", totalCount, pageSize, pageCount));
        if (pageCount > 1) {
            for (int pageIndex = 2; pageIndex <= pageCount; pageIndex++) {
                pagerTasks.add(this.submit(new PagerTask(pager.getPageSize(), pageIndex, pagerExecutor, pagerRecordsExecutor, pagerTaskResult)));
            }

            for (Future<List<T>> future : pagerTasks) {
                try {
                    List<T> pagerResult = future.get();//获取执行结果
                    if (!CollectionUtils.isEmpty(pagerResult) && resultMessages.size() < DEFAULT_RESULT_MESSAGE_CAPACITY) {
                        //只保留前2000以内的执行结果集合
                        resultMessages.addAll(pagerResult);
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                } catch (ExecutionException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        logger.info(MessageFormat.format("执行完成，总计耗时{0}毫秒", System.currentTimeMillis() - startTime));
    }


    /**
     * 查询并处理第一页数据
     *
     * @param resultMessages
     * @param pager
     * @param pagerExecutor
     * @param pagerRecordsExecutor
     * @return
     */
    private long firstPageExecute(List<T> resultMessages,
                                  Pager pager,
                                  Function<Pager, PagerRecords> pagerExecutor,
                                  Function<PagerRecords, List<T>> pagerRecordsExecutor) {
        pager.setPageIndex(1);//从第一页开始
        pager.setPageType(Pager.QUERY_TYPE_ALL);//第一页查询时需要返回记录总数
        //第一次分页查询，需要获取totalCount
        PagerRecords pagerRecords = pagerExecutor.apply(pager);//返回分页查询结果
        if (pagerRecords == null) {
            return 0;
        }
        List<T> firstResult = pagerRecordsExecutor.apply(pagerRecords);//执行分页查询结果

        if (!CollectionUtils.isEmpty(firstResult)) {
            resultMessages.addAll(firstResult);
        }
        return pagerRecords.getTotalCount();
    }


    /**
     * 分页任务
     */
    private static class PagerTask<T> implements Callable<List<T>> {

        private static final Log logger = LogFactory.getLog(PagerTask.class);

        private int pageSize;//每页记录条数

        private int pageIndex;//当前页

        private Function<Pager, PagerRecords> pagerExecutor;//传人pager，获取分页数据

        private Function<PagerRecords, List<T>> pagerRecordsExecutor;//处理分页数据，并返回执行结果集合

        private PagerTaskResult pagerTaskResult;

        public PagerTask(int pageSize, int pageIndex,
                         Function<Pager, PagerRecords> pagerExecutor,
                         Function<PagerRecords, List<T>> pagerRecordsExecutor,
                         PagerTaskResult pagerTaskResult) {
            this.pageSize = pageSize;
            this.pageIndex = pageIndex;
            this.pagerExecutor = pagerExecutor;
            this.pagerRecordsExecutor = pagerRecordsExecutor;
            this.pagerTaskResult = pagerTaskResult;
        }

        @Override
        public List<T> call() throws Exception {
            Pager pager = new Pager(pageSize, pageIndex, Pager.QUERY_TYPE_LIST);
            try {
                PagerRecords pagerRecords = pagerExecutor.apply(pager);

                if (pagerRecords == null) {
                    return new ArrayList<T>();
                }
                pagerRecords.setPager(pager);
                return pagerRecordsExecutor.apply(pagerRecords);
            } catch (Exception e) {
                //
                e.printStackTrace();
                logger.error(pageIndex + ":" + e.getMessage());
            } finally {
                this.pagerTaskResult.increaseExecCount();
                logger.info(MessageFormat.format("执行第{0}页任务,当前进度{1}", pageIndex, pagerTaskResult.getProgress()));
            }
            return new ArrayList<T>();
        }
    }
}
