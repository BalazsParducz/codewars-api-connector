package com.codewarsapi.config;

import com.codewarsapi.model.Mentor;
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

    private String password;

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

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
            password = bCryptPasswordEncoder().encode(System.getenv("adminpassword"));

            auth    .inMemoryAuthentication()
                    .withUser("admin")
                    .password(password)
                    .roles("ADMIN");

            auth    .userDetailsService(mentorDetailsServiceImpl);
    }


    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {

            httpSec.csrf().disable()


                    .authorizeRequests()
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .antMatchers("/registration","/**.png", "/**.css").permitAll()
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
