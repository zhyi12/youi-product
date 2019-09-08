package org.youi.metadata.common.model;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;

/**
 * Created by zhouyi on 2019/9/7.
 */
public interface IMetaObject extends Domain{

    /**
     *
     * @return
     */
    String getId();
    /**
     *
     * @return
     */
    Record getProperties();
}
