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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "youi.ods.cassandra",
        ignoreInvalidFields = true)
public class OdsCassandraProperties {

    private String host = CassandraClusterFactoryBean.DEFAULT_CONTACT_POINTS;

    private String keySpaceName = "dw";

    private int port = CassandraClusterFactoryBean.DEFAULT_PORT;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getKeySpaceName() {
        return keySpaceName;
    }

    public void setKeySpaceName(String keySpaceName) {
        this.keySpaceName = keySpaceName;
    }
}
