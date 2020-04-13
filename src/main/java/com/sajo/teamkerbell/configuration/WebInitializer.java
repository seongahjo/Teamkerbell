package com.sajo.teamkerbell.configuration;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    //SecurityConfig.class, RedisConfig.class
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{JpaConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected String getServletName() {
        return "dispatcher";
    }

//    @Override
//    protected Filter[] getServletFilters() {
//        DelegatingFilterProxy session = new DelegatingFilterProxy("springSessionRepositoryFilter");
//        DelegatingFilterProxy security = new DelegatingFilterProxy("springSecurityFilterChain");
//
//        return new Filter[]{session, security};
//    }

}
