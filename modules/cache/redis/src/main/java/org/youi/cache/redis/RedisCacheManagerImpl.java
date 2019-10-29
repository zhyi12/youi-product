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
package org.youi.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.youi.cache.ICacheManager;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class RedisCacheManagerImpl extends RedisCacheManager implements ICacheManager {

    /**
     *
     * @param cacheWriter
     * @param defaultCacheConfiguration
     */
    @Autowired
    public RedisCacheManagerImpl(
            RedisCacheWriter cacheWriter,
            RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    public Object getDataFromCache(String cacheName, String dataKey) {
        Cache repairDataCache = getCache(cacheName);
        if (repairDataCache != null) {
            Cache.ValueWrapper value = repairDataCache.get(dataKey);
            if (value != null) {
                return value.get();
            }
        }
        return null;
    }

}
