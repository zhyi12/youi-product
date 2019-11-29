/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.datacenter.ods.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.youi.framework.context.annotation.Module;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Configuration
@Module(name = "datacenter.ods.cassandra",caption = "datacenter.ods.cassandra")
@EnableConfigurationProperties(OdsCassandraProperties.class)
public class ModuleConfig extends AbstractCassandraConfiguration {

    @Autowired
    private OdsCassandraProperties odsCassandraProperties;

    @Bean
    public CqlTemplate cqlTemplate(SessionFactory sessionFactory){
        return new CqlTemplate(sessionFactory);
    }

    @Bean
    public OdsCassandraTableBuilder odsCassandraTableBuilder(){
        return new OdsCassandraTableBuilder();
    }

    /*
     * Provide a contact point to the configuration.
     */
    public String getContactPoints() {
        return odsCassandraProperties.getHost();
    }

    @Override
    protected int getPort() {
        return odsCassandraProperties.getPort();
    }

    @Override
    protected String getKeyspaceName() {
        return odsCassandraProperties.getKeySpaceName();
    }
}
