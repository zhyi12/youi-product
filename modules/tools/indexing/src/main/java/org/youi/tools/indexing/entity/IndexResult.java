package org.youi.tools.indexing.entity;

import org.youi.framework.core.dataobj.Domain;

public class IndexResult implements Domain{

    private String id;

    private String text;

    private String fullText;

    private Float score;

    private String indexName;

    private String group;//分组

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "IndexResult{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                '}';
    }
}
