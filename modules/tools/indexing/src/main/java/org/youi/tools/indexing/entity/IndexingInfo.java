package org.youi.tools.indexing.entity;

import org.youi.framework.core.dataobj.Domain;

import java.util.List;

/**
 * Created by zhouyi on 2017/6/12.
 */
public class IndexingInfo implements Domain{

    private String name;//索引名

    private String caption;//索引描述

    private String idAttr;//数据集id属性

    private String textAttr;//数据集文本属性

    private String metaObjectNameAttr;

    private String agencyCode; //机构代码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getIdAttr() {
        return idAttr;
    }

    public void setIdAttr(String idAttr) {
        this.idAttr = idAttr;
    }

    public String getTextAttr() {
        return textAttr;
    }

    public void setTextAttr(String textAttr) {
        this.textAttr = textAttr;
    }

    public String getMetaObjectNameAttr() {
        return metaObjectNameAttr;
    }

    public void setMetaObjectNameAttr(String metaObjectNameAttr) {
        this.metaObjectNameAttr = metaObjectNameAttr;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
}
