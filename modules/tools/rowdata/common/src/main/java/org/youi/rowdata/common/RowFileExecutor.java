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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.youi.framework.core.dataobj.Domain;
import org.youi.rowdata.common.model.BatchResult;
import org.youi.rowdata.common.model.RowResult;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class RowFileExecutor<R extends Domain> extends ThreadPoolTaskExecutor {

    private int batchSize = 1000;

    private final Object batchMonitor = new Object();

    private Map<String, BatchData> batchDatas = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void initialize() {
        super.initialize();
        this.setCorePoolSize(10);
    }

    /**
     * 启动文件一个执行器
     *
     * @return
     */
    protected String start() {
        String uuid = UUID.randomUUID().toString();
        batchDatas.put(uuid, new BatchData());
        return uuid;
    }

    /**
     *
     * @param instanceId
     * @param rowIndex
     * @param rowReader
     * @param rowMapper
     * @param batchExecutor
     */
    protected  void processRow(
            String instanceId,
            int rowIndex,
            Function<Integer, RowResult> rowReader,
            Function<RowResult, R> rowMapper,
            Function<List<R>, BatchResult> batchExecutor) {
        RowResult rowResult = rowReader.apply(rowIndex);//获取行数据
        if(rowResult==null){
            return;
        }
        BatchData<R> batchData = batchDatas.get(instanceId);
        batchData.getBatchRowDatas().add(rowMapper.apply(rowResult));//行数据转换成对象

        synchronized (this.batchMonitor) {
            if (batchData.getBatchRowDatas().size() == batchSize) {
                batchData.getFutures().add(this.submit(new BatchExecutor(batchExecutor,
                        new ArrayList(batchData.getBatchRowDatas()),batchData.getCurrentIndex().addAndGet(1))));//批量提交任务
                batchData.getBatchRowDatas().clear();
                logger.info("加入第"+batchData.getCurrentIndex().get()+"批次任务");
            }
        }
    }

    /**
     * @param instanceId
     */
    protected List<BatchResult> close(String instanceId,Function<List<R>, BatchResult> batchExecutor) {
        BatchData<R> batchData = batchDatas.get(instanceId);

        if(batchData.getBatchRowDatas().size()>0){
            batchData.getFutures().add(this.submit(new BatchExecutor(batchExecutor,
                    new ArrayList(batchData.getBatchRowDatas()),batchData.getCurrentIndex().addAndGet(1))));//批量提交任务
        }

        List<BatchResult> batchResults = new ArrayList<>();
        for (Future<BatchResult> future : batchData.getFutures()) {
            try {
                BatchResult batchResult = future.get();
                if(batchResult!=null){
                    batchResults.add(batchResult);
                }
            } catch (InterruptedException e) {
                logger.warn(e.getMessage());
            } catch (ExecutionException e) {
                logger.warn(e.getMessage());
                e.printStackTrace();
            } catch (Exception e){
                logger.warn(e.getMessage());
            }
        }

        batchDatas.remove(instanceId);
        return batchResults;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    /**
     *
     * @param <R>
     */
    private static class BatchData<R> {

        private Collection<R> batchRowDatas = Collections.synchronizedList(new ArrayList<>());

        private AtomicInteger currentIndex = new AtomicInteger();

        List<Future> futures = new ArrayList<>();

        public Collection<R> getBatchRowDatas() {
            return batchRowDatas;
        }

        public void setBatchRowDatas(Collection<R> batchRowDatas) {
            this.batchRowDatas = batchRowDatas;
        }

        public List<Future> getFutures() {
            return futures;
        }

        public void setFutures(List<Future> futures) {
            this.futures = futures;
        }

        public AtomicInteger getCurrentIndex() {
            return currentIndex;
        }

        public void setCurrentIndex(AtomicInteger currentIndex) {
            this.currentIndex = currentIndex;
        }
    }

    private static class BatchExecutor<R> implements Callable<BatchResult> {

        private static final Log logger = LogFactory.getLog(BatchExecutor.class);

        private Function<List<R>, BatchResult> batchExecutor;

        private List rowDatas;

        private int batchIndex;

        public int getBatchIndex() {
            return batchIndex;
        }

        public void setBatchIndex(int batchIndex) {
            this.batchIndex = batchIndex;
        }

        public BatchExecutor(Function<List<R>, BatchResult> batchExecutor,
                             List rowDatas,int batchIndex) {
            this.batchExecutor = batchExecutor;
            this.rowDatas = rowDatas;
            this.batchIndex = batchIndex;
        }

        @Override
        public BatchResult call() throws Exception {
            int count = rowDatas!=null?rowDatas.size():0;
            BatchResult batchResult = batchExecutor.apply(rowDatas);

            if(batchResult!=null){
                batchResult.setBatchCount(count);
                batchResult.setBatchIndex(batchIndex);

                if(batchResult!=null){
                    logger.info("执行第"+batchResult.getBatchIndex()+"批次任务,执行情况:共"+batchResult.getBatchCount()+"行数据"+ (StringUtils.isNotEmpty(batchResult.getMessage())?(","+batchResult.getMessage()+"."):"."));
                }
                return batchResult;
            }
            return null;
        }
    }
}
