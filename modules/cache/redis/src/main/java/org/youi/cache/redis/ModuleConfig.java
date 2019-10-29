/*
 * YOUI框架
 * Copyright 2016 the original author or authors.
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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.youi.cache.ICacheManager;
import org.youi.framework.context.annotation.Module;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 *
 * @author 代码生成器
 * @since 1.0.0
 */
@Configuration("redis.config")
@Module(name = "redis", caption = "redis")
@EnableRedisRepositories
public class ModuleConfig {

    /**
     * 使用byte作为健值对
     * @param factory
     * @return
     */
    @SuppressWarnings("all")
    @Bean
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<byte[],byte[]> template=new RedisTemplate<byte[], byte[]>();
        template.setConnectionFactory(factory);
        return template;
    }

    /**
     * 无锁的RedisCacheWriter
     * @param factory
     * @return
     */
    @SuppressWarnings("all")
    @Bean
    public RedisCacheWriter redisCacheWriter(RedisConnectionFactory factory){
        return RedisCacheWriter.nonLockingRedisCacheWriter(factory);
    }

    /**
     *
     * @param redisCacheWriter
     * @return
     */
    @Bean
    public ICacheManager redisCacheManager(RedisCacheWriter redisCacheWriter){
        return new RedisCacheManagerImpl(redisCacheWriter,RedisCacheConfiguration.defaultCacheConfig());
    }
}
