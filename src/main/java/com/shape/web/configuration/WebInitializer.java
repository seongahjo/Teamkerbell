package com.shape.web.configuration;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
   /* private static final String CONFIG_LOCATION = "com.shape.web.configuration";
    private static final String MAPPING_URL ="/";

   @Override
    public void onStartup(ServletContext servletContext) throws ServletException{
        WebApplicationContext context=getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(MAPPING_URL);

    }
    private AnnotationConfigWebApplicationContext getContext(){
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        return context;
    }*/


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{JpaConfig.class};
        //SecurityConfig.class, RedisConfig.class
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

    @Override
    protected Filter[] getServletFilters() {
        DelegatingFilterProxy session = new DelegatingFilterProxy("springSessionRepositoryFilter");
        DelegatingFilterProxy security = new DelegatingFilterProxy("springSecurityFilterChain");

        return new Filter[]{session, security};
    }

}
