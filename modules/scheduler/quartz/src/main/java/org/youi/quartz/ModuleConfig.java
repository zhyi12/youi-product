/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.youi.framework.context.annotation.Module;
import org.youi.framework.jpa.hibernate.BaseDaoHibernate;

import javax.sql.DataSource;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午11:48:47</p>
 */
@Configuration("quartz.config")
@Module(name = "quartz",caption = "quartz")
@ComponentScan(basePackages = {"org.youi.quartz.service.impl"})
@EnableJpaRepositories(basePackages ={ "org.youi.quartz.dao"},repositoryBaseClass=BaseDaoHibernate.class)
public class ModuleConfig {

    @Autowired(required = false)
    private DataSource dataSource;

    @Value("classpath:youi_quartz.properties")
    private Resource resource;

    /**
     * 定时器配置
     * @return
     */
    @Bean(value = "schedulerFactoryBean",destroyMethod = "destroy")
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //数据资源
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setConfigLocation(resource);
        return  schedulerFactoryBean;
    }

}