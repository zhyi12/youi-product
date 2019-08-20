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
package org.youi.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.youi.framework.web.security.*;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.oauth2.sso.logout-uri}")
    private String logoutUri;

    @Value("${security.oauth2.sso.app-login-uri}")
    private String appLoginUri;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login**","/uaa/**","/logout")
                .permitAll()
                .antMatchers("/*/services/**/*.json").access("@webAccessFactory.apiAccess(authentication,request)")
                .antMatchers("/page/**/*.html").access("@webAccessFactory.pageAccess(authentication,request)")
                .anyRequest()
                .authenticated().and().httpBasic().disable()
                .logout().logoutSuccessUrl(logoutUri+"?appUrl="+appLoginUri).and()
                .csrf().ignoringAntMatchers("/uaa/**","/logout","/workflowServices/services/**",
                    "/gatheringServices/services/gatherDataCheckManager/*")
                .and().cors().disable()
                .addFilterAfter(apiSecurityFilter(), SessionManagementFilter.class);
    }

    @Bean
    public ApiSecurityFilter apiSecurityFilter(){
        return new ApiSecurityFilter();
    }


    @Bean
    public UserInfoService userInfoService(){
        return new UserInfoService();
    }

    @Bean
    public WebAccessFactory webAccessFactory(){
        return new WebAccessFactory();
    }

    @Bean
    public WebSecurityServices webSecurityServices(){
        return new WebSecurityServices();
    }

    @Bean
    public WebCsrfTokenParser webCsrfTokenParser(){
        return new WebCsrfTokenParser();
    }

}
