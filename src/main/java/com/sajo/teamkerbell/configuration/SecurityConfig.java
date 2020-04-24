package com.sajo.teamkerbell.configuration;

import com.sajo.teamkerbell.security.CustomAuthenticationSuccessHandler;
import com.sajo.teamkerbell.security.RestAuthenticationEntryPoint;
import com.sajo.teamkerbell.security.RestLoginFailureHandler;
import com.sajo.teamkerbell.service.UserAuthService;
import com.sajo.teamkerbell.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Created by seongahjo on 2016. 7. 14..
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthService userAuthService;
    private final UserService userService;


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService);
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
    public CustomAuthenticationSuccessHandler authenticationHandler() {
        return new CustomAuthenticationSuccessHandler(userService);
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
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/", "/register", "/login", "/user").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/")
                .defaultSuccessUrl("/dashboard")
                .usernameParameter("userId")
                .passwordParameter("pw")
                .successHandler(authenticationHandler())
                .failureHandler(restLoginFailureHandler())
                .permitAll();

    }

}
