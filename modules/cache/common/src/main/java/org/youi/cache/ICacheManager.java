package org.youi.cache;

import org.springframework.cache.CacheManager;

/**
 * Created by zhouyi on 2019/10/12.
 */
public interface ICacheManager extends CacheManager{

    /**
     * 获取缓存数据
     * @param cacheName
     * @param dataKey
     * @return
     */
    Object getDataFromCache(String cacheName, String dataKey);
}
