package org.youi.tools.indexing.entity;

import java.util.ArrayList;
import java.util.List;

public class PagerIndexResult {

    private List<IndexResult> records;
    private int totalCount = 0;

    public List<IndexResult> getRecords() {
        if(records == null){
            return new ArrayList<IndexResult>();
        }
        return records;
    }

    public void setRecords(List<IndexResult> records) {
        this.records = records;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
