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
package org.youi.rowdata.xls;

import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.Domain;
import org.youi.rowdata.common.RowFileExecutor;
import org.youi.rowdata.common.model.BatchResult;
import org.youi.rowdata.common.model.RowResult;
import org.youi.rowdata.xls.util.XlsRowFileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsRowFileExecutor<R extends Domain> extends RowFileExecutor<R> {

    /**
     * xls文件处理
     * @param file
     * @param rowMapper
     * @param batchExecutor
     */
    public List<BatchResult> processFile(File file,
                                         Function<RowResult, R> rowMapper, Function<List<R>, BatchResult> batchExecutor) {

        List<BatchResult> batchResults = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        logger.info("开始执行导入文件：" + file.getAbsolutePath());
        try (InputStream inputStream = new FileInputStream(file)) {

            List<String> rowHeaders = new ArrayList<>();
            String uuid = this.start();
            XlsRowFileUtils.precessRow(inputStream, (rowResult -> {
                this.processRow(uuid, rowResult.getRowIndex(), (rowIndex) -> {
                    if (rowIndex == 0) {
                        rowHeaders.addAll(CollectionUtils.arrayToList(rowResult.getRowData()));
                        return null;
                    } else {
                        rowResult.setHeaders(rowHeaders);
                    }
                    return rowResult;
                }, rowMapper, batchExecutor);
                return rowResult.getRowIndex();
            }));
            //批量执行
            batchResults = this.close(uuid, batchExecutor);//返回执行结果
            logger.info("文件" + file.getAbsolutePath() + "导入成功,共耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            //
        }
        return batchResults;
    }

    /**
     * 返回xls第一行数据
     * @param file
     * @return
     */
    public String[] parseFileHeaders(File file) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return XlsRowFileUtils.readerHeader(inputStream);
        } catch (IOException e) {
            //
        }
        return new String[0];
    }

}
