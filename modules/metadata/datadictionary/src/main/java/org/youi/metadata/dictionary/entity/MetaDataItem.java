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
package org.youi.metadata.dictionary.entity;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.metadata.common.model.IMetaObject;

import javax.persistence.Column;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;
import java.util.Map;

/**
 * 元数据字典项
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_metadata_item")
public class MetaDataItem implements IMetaObject {

    @Id
    private String id;

    @Column
    private String text;

    private String folderId;//字典项文件夹  - 数据元分类编号

    private String dataType;//数据类型

    private String oldName;//元数据标准名称(原名称)

    @Column
    private String name;//数据字段名称(修改后)

    @Column
    private String code;//数据项编号

    @Column
    private String convert;//代码集

    private String description;//元数据描述 - 说明

    @Column
    private String version;//版本

    private Long updateTime;//变更时间

    private String updateDescription;//变更内容

    private String updateOperator;//变更人

    private String owner;//    数据责任主体

    private String grade;//    标准层级

    private String  scope;//    备注适用范围

    private String accordingTo;//    国标数据源编号

    private String dataLength;//数据长度

    private String dataPrecision;//数据精度

    @Column
    private String dataFormat;//数据格式

    private String defaultValue;//默认值

    private Boolean nullable = Boolean.TRUE;

    private Map<String, String[]> datas = new HashMap<>();

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getDataLength() {
        return dataLength;
    }

    public void setDataLength(String dataLength) {
        this.dataLength = dataLength;
    }

    public String getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(String dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public String getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateDescription() {
        return updateDescription;
    }

    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    public String getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(String updateOperator) {
        this.updateOperator = updateOperator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAccordingTo() {
        return accordingTo;
    }

    public void setAccordingTo(String accordingTo) {
        this.accordingTo = accordingTo;
    }

    public String getConvert() {
        return convert;
    }

    public void setConvert(String convert) {
        this.convert = convert;
    }

    @Override
    public Map<String, String[]> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, String[]> datas) {
        this.datas = datas;
    }


}
