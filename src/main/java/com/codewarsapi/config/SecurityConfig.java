package com.codewarsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Autowired
//    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
//            auth
//                    .userDetailsService()
////                .inMemoryAuthentication()
////                    .withUser("B-dog")
////                    .password("hungary")
////                    .roles("USER");
//    }


    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
            httpSec
                    .authorizeRequests()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/registration").permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .and()
                    .logout()
                        .logoutSuccessUrl("/login?logout")
                        .permitAll();
    }

}
