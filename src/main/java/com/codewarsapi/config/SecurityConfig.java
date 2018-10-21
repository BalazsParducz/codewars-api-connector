package com.codewarsapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService mentorDetailsServiceImpl;


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return mentorDetailsServiceImpl;
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(mentorDetailsServiceImpl);
    }


    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
            httpSec.csrf().disable()

                    .authorizeRequests()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/registration","/codecool_symbol_flat.png", "/basic.css", "https://codewars-api-connector.herokuapp.com/").permitAll()
                        .anyRequest().authenticated()           
                        .and()
                    .formLogin()
                        .loginPage("/login").permitAll()
                        .successHandler(new SimpleUrlAuthenticationSuccessHandler())
                        .and()
                    .logout()
                        .logoutSuccessUrl("/login?logout")
                        .permitAll();
    }

}
