package com.example.BankingSystem.controller.security;

import com.example.BankingSystem.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(); // makes it impossible to set public access?
        http.csrf().disable();
        http.authorizeRequests()
//                .mvcMatchers(HttpMethod.GET, "/hello-world").authenticated()
//                .mvcMatchers(HttpMethod.GET, "/courses").authenticated()
//                .mvcMatchers(HttpMethod.GET, "/products").authenticated()
//                .mvcMatchers(HttpMethod.GET, "/products/**").hasAnyRole("ADMIN","USER")
//                .mvcMatchers(HttpMethod.POST, "/products").hasAnyRole("ADMIN","TECHNICIAN")
//                .mvcMatchers(HttpMethod.GET, "/checking").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accountholder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET, "/accountholder").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accountholder/*").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.PUT, "/accountholder/*").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/accountholder/*").hasRole("ADMIN")
                .anyRequest().permitAll();
    }
}