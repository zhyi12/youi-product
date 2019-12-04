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
package org.youi.datacenter.batch;

import org.springframework.cloud.deployer.resource.maven.MavenProperties;
import org.springframework.cloud.deployer.resource.maven.MavenResource;
import org.springframework.cloud.deployer.spi.core.AppDefinition;
import org.springframework.cloud.deployer.spi.core.AppDeploymentRequest;
import org.springframework.cloud.deployer.spi.local.LocalDeployerProperties;
import org.springframework.cloud.deployer.spi.local.LocalTaskLauncher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class BatchDeployTest {

    public static void main(String[] args) throws InterruptedException {
        LocalTaskLauncher launcher = new LocalTaskLauncher(new LocalDeployerProperties());
        String timestampId = launcher.launch(createAppDeploymentRequest("server-metadata"));
        for (int i = 0; i < 500; i++) {
            Thread.sleep(100);
            System.out.println("timestamp: " + launcher.status(timestampId));
        }
        // timestamp completes quickly, but we can 'cancel' the running task
        launcher.cancel(timestampId);
        System.out.println("timestamp after cancel: " + launcher.status(timestampId));
    }

    private static AppDeploymentRequest createAppDeploymentRequest(String app) {

        MavenProperties mavenProperties = new MavenProperties();
        Map<String, MavenProperties.RemoteRepository> remoteRepositories = new HashMap<>();

        MavenProperties.RemoteRepository remoteRepository = new MavenProperties.RemoteRepository();

        remoteRepository.setUrl("http://49.4.13.100:8081/repository/maven-releases");
        remoteRepositories.put("releases",remoteRepository);

        mavenProperties.setRemoteRepositories(remoteRepositories);
        MavenResource resource = new MavenResource.Builder(mavenProperties)
                .artifactId(app)
                .groupId("org.youi.product")
                .version("1.0.0-SNAPSHOT")
                .build();
        Map<String, String> properties = new HashMap<>();
        properties.put("server.port", "8970");
        AppDefinition definition = new AppDefinition(app, properties);
        AppDeploymentRequest request = new AppDeploymentRequest(definition, resource);
        return request;
    }
}
