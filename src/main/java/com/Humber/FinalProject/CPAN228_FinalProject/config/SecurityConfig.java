package com.Humber.FinalProject.CPAN228_FinalProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()

                //this is needed to allow the rest controller to accept POST requests
        ).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
