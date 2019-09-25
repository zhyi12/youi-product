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

import org.youi.framework.core.dataobj.Domain;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class PagerTaskResult implements Domain{

    private String id;

    private long totalCount;

    private long pageCount;

    private String message;

    private String status = "0";//执行状态 0-开始执行， 1-执行完成

    private AtomicInteger execCount = new AtomicInteger(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getProgress() {
        if(pageCount==0){
            return 0d;
        }
        return (execCount.get()*10000/pageCount)/100d;
    }

    public void increaseExecCount(){
        execCount.incrementAndGet();
    }
}
