package com.example.festival.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors((a) -> a.disable())
            .authorizeHttpRequests(auth -> {
                auth
                    .requestMatchers("/api/auth/**", "/api/festival/**", "/api/tour/**", "/api/**")
                    .permitAll()
                    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/user/**").hasAnyAuthority("ADMIN", "USER")
                    .anyRequest()
                    .authenticated();
            })
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
