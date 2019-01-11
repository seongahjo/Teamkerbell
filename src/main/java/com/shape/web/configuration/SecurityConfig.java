package com.shape.web.configuration;

import com.shape.web.security.CustomAuthenticationSucessHandler;
import com.shape.web.security.RestAuthenticationEntryPoint;
import com.shape.web.security.RestLoginFailureHandler;
import com.shape.web.service.LogsService;
import com.shape.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailService;

    private UserService userService;

    private LogsService logService;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailService, UserService userService, LogsService logService){
        this.userDetailService=userDetailService;
        this.userService=userService;
        this.logService=logService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
        // auth.inMemoryAuthentication().withUser("root").password("root").roles("USER");
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/resources/**", "/js/**", "/css/**", "/img/**");
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAuthenticationSucessHandler authenticationHandler() {
        return new CustomAuthenticationSucessHandler(userService,logService);
    }

    @Bean
    public RestLoginFailureHandler restLoginFailureHandler() {
        return new RestLoginFailureHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1);
        http
                .csrf().disable()
                //.exceptionHandling()
                //.authenticationEntryPoint(restAuthenticationEntryPoint())

                //.and()
                .authorizeRequests()
                .antMatchers("/", "/register", "/login", "/user").permitAll()
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
                .successHandler(authenticationHandler())
                //.failureHandler(restLoginFailureHandler())
                .permitAll();

    }

}
