package com.shape.web.configuration;

import com.shape.web.security.CustomAuthenticationSucessHandler;
import com.shape.web.service.LogService;
import com.shape.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
@Configuration
@EnableWebSecurity
@ComponentScan({"com.shape.web.service","com.shape.web.serviceImpl"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        // auth.inMemoryAuthentication().withUser("root").password("root").roles("USER");
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**", "/img/**");
    }

    @Bean
    public CustomAuthenticationSucessHandler authenticationHandler( ){return new CustomAuthenticationSucessHandler();}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/register", "/login","/user").permitAll()
                .antMatchers("/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/")
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("userId")
                .passwordParameter("pw")
                .permitAll()
        .successHandler(authenticationHandler());

    }

}
