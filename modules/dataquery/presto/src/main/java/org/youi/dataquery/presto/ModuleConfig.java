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
package org.youi.dataquery.presto;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.youi.dataquery.engine.core.CubeQuerySqlBuilder;
import org.youi.dataquery.presto.config.PrestoSourceProperties;
import org.youi.dataquery.presto.service.impl.PrestoCubeSqlBuilder;
import org.youi.dataquery.presto.service.impl.PrestoDataSource;
import org.youi.framework.context.annotation.Module;

import javax.sql.DataSource;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@Configuration("dataquery.presto.config")
@Module(name = "dataquery.presto",caption = "dataquery.presto")
@ComponentScan({"org.youi.dataquery.presto.dao","org.youi.dataquery.presto.service.impl"})
@EnableConfigurationProperties({PrestoSourceProperties.class})
public class ModuleConfig {

    @Autowired(required = false)
    private PrestoSourceProperties dataSourceProperties;

    @Bean
    public PrestoDataSource queryDataSource() {
        PrestoDataSource dataSource = new PrestoDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());// 用户名
        dataSource.setPassword(dataSourceProperties.getPassword());// 密码
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

    @Bean
    public JdbcTemplate queryJdbcTemplate(){
        return  new JdbcTemplate(queryDataSource());
    }

    @Bean
    public PrestoCubeSqlBuilder prestoCubeSqlBuilder(){
        return new PrestoCubeSqlBuilder();
    }
}
