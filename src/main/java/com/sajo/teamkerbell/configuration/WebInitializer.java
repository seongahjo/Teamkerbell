package com.sajo.teamkerbell.configuration;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{JpaConfig.class, EmbeddedRedisConfig.class, SecurityConfig.class, RedisConfig.class};
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
    protected WebApplicationContext createRootApplicationContext() {
        WebApplicationContext context = super.createRootApplicationContext();
        ((ConfigurableEnvironment) context.getEnvironment()).setActiveProfiles("redis");
        return context;
    }

    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy session = new DelegatingFilterProxy("springSessionRepositoryFilter");
        DelegatingFilterProxy security = new DelegatingFilterProxy("springSecurityFilterChain");
        return new Filter[]{session, security};
    }

    @Override
    protected String getServletName() {
        return "dispatcher";
    }
}
