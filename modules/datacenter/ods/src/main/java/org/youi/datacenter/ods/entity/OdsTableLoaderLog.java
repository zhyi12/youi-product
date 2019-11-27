/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.datacenter.ods.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;

/**
 * 贴源数据加载记录
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_dc_ods_table_loader_log")
public class OdsTableLoaderLog implements Domain {

    private static final long serialVersionUID = -3752451883622244836L;

    @Id
    private String id;

    @Column
    private Long startTime;//开始时间

    @Column
    private Long finishedTime;//完成时间

    private String content;//日志详情

    @Column
    private String dataResourceId;//

    @Column
    private String tableName;//表名

    @Column
    private String taskStatus;//任务状态

    public String getId() {
        return id;
    }

    public OdsTableLoaderLog setId(String id) {
        this.id = id;
        return this;
    }

    public Long getStartTime() {
        return startTime;
    }

    public OdsTableLoaderLog setStartTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getFinishedTime() {
        return finishedTime;
    }

    public OdsTableLoaderLog setFinishedTime(Long finishedTime) {
        this.finishedTime = finishedTime;
        return this;
    }

    public String getContent() {
        return content;
    }

    public OdsTableLoaderLog setContent(String content) {
        this.content = content;
        return this;
    }

    public String getDataResourceId() {
        return dataResourceId;
    }

    public OdsTableLoaderLog setDataResourceId(String dataResourceId) {
        this.dataResourceId = dataResourceId;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public OdsTableLoaderLog setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public OdsTableLoaderLog setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }
}
