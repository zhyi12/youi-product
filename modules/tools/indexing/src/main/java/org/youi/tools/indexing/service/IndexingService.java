package org.youi.tools.indexing.service;

import org.apache.lucene.document.TextField;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.tools.indexing.entity.IndexResult;
import org.youi.tools.indexing.entity.MatchingItem;
import org.youi.tools.indexing.entity.PagerIndexResult;

import java.util.List;
import java.util.function.Function;

/**
 * 索引服务
 */
public interface IndexingService {


    /**
     * 文本匹配
     * @param items
     * @param records
     * @param function
     * @return
     */
    List<MatchingItem> matchingItems(List<Item> items, List<? extends Domain> records,
                                     Function<Domain,List<TextField>> function);
}
