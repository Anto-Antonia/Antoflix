package com.example.Antoflix.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/api/role").permitAll() // ca sa pot adauga roluri ( in practica ar trebui protejat)
                    .requestMatchers("/api/register").permitAll() // pentru a inregistra un user cu un anumit rol(in practica doar un utilizator simplu ar trebui sa se poata inregistra cu rol de user)
                    .requestMatchers("/api/signin").permitAll() // pentru a loga un utilizator
                    .requestMatchers(HttpMethod.GET, "/api/products").hasAuthority("user") // endpoint protejat( doar un user il poate accesa)
                    .requestMatchers(HttpMethod.POST, "/api/product").hasAuthority("admin") //endpoint protejat(doar un admin il poate accesa)
                    .anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

}
