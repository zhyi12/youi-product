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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.theme.SessionThemeResolver;
import org.youi.decorator.modern.ModuleConfig;
import org.youi.framework.web.ServerUrlTransformer;
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

    @Autowired
    private ThemeResolver themeResolver;

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

    /**
     * 页面主题选择
     * @param request
     * @param response
     * @param theme
     * @return
     */
    @RequestMapping("/theme/{theme}.json")
    @ResponseBody
    public String theme(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable("theme") String theme){
        themeResolver.setThemeName(request,response,theme);
        return "{\"theme\":\""+theme+"\"}";
    }

    @Bean(name = "themeResolver")
    public ThemeResolver sessionThemeResolver(){
        return new SessionThemeResolver();
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    /**
     * 配置服务url转换规则，在jsp页面中可以支持{serverName}.{serversName}.{serviceName} 格式的简写路径
     * @return
     */
    @Bean
    public ServerUrlTransformer serverUrlTransformer(){
        return new ServerUrlTransformer();
    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(new Class<?>[]{WebConfig.class,
                WebSecurityConfig.class, ModuleConfig.class,
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
                WebSecurityConfig.class,ModuleConfig.class,
                WebStarter.class);
    }
}
