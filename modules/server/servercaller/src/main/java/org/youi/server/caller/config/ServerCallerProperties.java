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
package org.youi.server.caller.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@ConfigurationProperties(prefix = "youi.server.caller")
public class ServerCallerProperties {

    private String serverPrefix = "_MICRO_SERVER_";

    private String keystoreName;
    private String keystoreFile;
    private String keystorePassword;

    private String apiBaseUri;

    public String getKeystoreName() {
        return keystoreName;
    }

    public void setKeystoreName(String keystoreName) {
        this.keystoreName = keystoreName;
    }

    public String getKeystoreFile() {
        return keystoreFile;
    }

    public void setKeystoreFile(String keystoreFile) {
        this.keystoreFile = keystoreFile;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getApiBaseUri() {
        return apiBaseUri;
    }

    public void setApiBaseUri(String apiBaseUri) {
        this.apiBaseUri = apiBaseUri;
    }

    public String getServerPrefix() {
        return serverPrefix;
    }

    public void setServerPrefix(String serverPrefix) {
        this.serverPrefix = serverPrefix;
    }
}
