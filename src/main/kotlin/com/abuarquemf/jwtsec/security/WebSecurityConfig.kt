package com.abuarquemf.jwtsec.security

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http!!.csrf().disable().authorizeRequests()
                 //allow access to these routes without authentication
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .anyRequest().authenticated()
                .and()
                //filter for sign up
                .addFilterBefore(JWTSignUpFilter("/users/signup"),
                        BasicAuthenticationFilter::class.java)
                //filter for sign in
                .addFilterBefore(JWTSignInFilter("/users"),
                        BasicAuthenticationFilter::class.java)
                //filtering other requests
                .addFilterBefore(JWTAuthenticationFilter(), BasicAuthenticationFilter::class.java)
    }
}
