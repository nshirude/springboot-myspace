package com.myspace.subscription.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	public static String CLASS_NAME = SecurityConfig.class.getName();


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	logger.info("Inside method configure ", CLASS_NAME);
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password("password").roles("ADMIN");      
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	logger.info("Inside method configure with http security", CLASS_NAME);
        httpSecurity
                .authorizeRequests()
                .antMatchers("/api/**")
                //.permitAll()
                .authenticated()
                .and()
                .httpBasic();
        httpSecurity.csrf().disable();

    }
  
}
