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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.ModelAndView;
import org.youi.framework.web.WebConfig;
import org.youi.framework.web.security.WebSecurityConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@SpringBootApplication
@Controller
@EnableZuulProxy
@EnableEurekaClient
public class WebStarter extends SpringBootServletInitializer {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainIndex() {
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "error";
    }

    @RequestMapping("/{menuId}/index.html")
    public ModelAndView barIndex(
            HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("menuId") String menuId) {
        ModelAndView model = new ModelAndView("index");
        //if(account!=null){
        model.addObject("barMenuId", menuId);
        //}
        return model;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }


    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(new Class<?>[]{WebConfig.class,
                WebSecurityConfig.class,
                WebStarter.class}, args);
    }

    /**
     * war 包启动
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebConfig.class,
                WebSecurityConfig.class,
                WebStarter.class);
    }
}
