package com.errabi.ishop.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests((requests) -> {
                    requests.antMatchers("/","/webjars/**","/resources/**").permitAll();
                    //requests.antMatchers(HttpMethod.GET,"/api/v1/users/**").permitAll();
                    requests.mvcMatchers(HttpMethod.GET,"/api/v1/users/{userId}").permitAll();
                        })
                        .authorizeRequests()
                        .anyRequest()
                        .authenticated()
                        .and()
                        .formLogin()
                        .and()
                        .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("ayoub")
                .password("{noop}admin")
                .roles("ADMIN");
     }

}
