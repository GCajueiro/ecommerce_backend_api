package com.ecommerce.liven.ecommerce_backend.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SuppressWarnings("ALL")
@Configuration
public class ConfigSecurity {

    private JWTRequestFilter jwtRequestfilter;

    public ConfigSecurity(JWTRequestFilter jwtRequestfilter) {
        this.jwtRequestfilter = jwtRequestfilter;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestfilter, UsernamePasswordAuthenticationFilter.class);
        http.authorizeRequests()
                .requestMatchers("/auth/register","/auth/login","/produto/buscar","localhost:8080/documentation").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}
