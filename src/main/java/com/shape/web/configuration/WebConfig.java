package com.shape.web.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by seongahjo on 2016. 7. 14..
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.shape.web.controller"})
public class WebConfig extends WebMvcConfigurerAdapter{
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry){
            registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
            registry.addResourceHandler("/favicon.ico").addResourceLocations("/img/fairy.ico");
        }

        //EncodingFilter
        @Bean
        public CharacterEncodingFilter characterEncodingFilter(){
            final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
            characterEncodingFilter.setEncoding("UTF-8");
            characterEncodingFilter.setForceEncoding(true);
            return characterEncodingFilter;
        }

        @Bean
        public MessageSource messageSource(){
            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("message/messages");
            return messageSource;
        }
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
            configurer.enable();
        }

        //InternalResourceViewResolver
        @Bean
        public InternalResourceViewResolver getInternalResourceViewResolver(){
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
            resolver.setPrefix("/WEB-INF/views/");
            resolver.setSuffix(".jsp");
            return resolver;
        }





}
