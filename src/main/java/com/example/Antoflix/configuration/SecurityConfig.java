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
        http.authorizeHttpRequests(auth -> auth     // removed auth -> { auth

                    .requestMatchers("/", "/homepage").permitAll()
                  //  .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                    //.requestMatchers("/api/v1/users/role").permitAll() // ca sa pot adauga roluri ( in practica ar trebui protejat)
                  //  .requestMatchers("/api/register").permitAll() // pentru a inregistra un user cu un anumit rol(in practica doar un utilizator simplu ar trebui sa se poata inregistra cu rol de user)
                 //   .requestMatchers("/api/signIn").permitAll() // pentru a loga un utilizator
                        .requestMatchers("/", "/register", "/signIn", "/css/**", "/js/**", "/images/**").permitAll()
                    .requestMatchers("/api/logout").permitAll() // pentru a deloga un user

                    .requestMatchers(HttpMethod.GET, "/api/v1/movies").hasAuthority("user") // endpoint protejat( doar un user il poate accesa)
                    .requestMatchers(HttpMethod.POST, "/api/v1/movies/movie").hasAuthority("admin")//endpoint protejat(doar un admin il poate accesa)
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/{userId}/favorites").hasAuthority("user")
                    .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAuthority("admin")
                    .requestMatchers(HttpMethod.POST, "/api/v1/movies/genre").hasAuthority("admin")
                    .requestMatchers(HttpMethod.PATCH, "/api/v1/movies/genre/{id}").hasAuthority("admin")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/movies/{id}").hasAuthority("admin")
                    .requestMatchers(HttpMethod.DELETE, "/api/v1/movies/genre/{id}").hasAuthority("admin")
                    .requestMatchers(HttpMethod.GET, "/api/v1/movies/genres").hasAuthority("admin")
                    .requestMatchers(HttpMethod.GET, "/api/v1/watchlist/**").hasAuthority("user")
                    .requestMatchers(HttpMethod.POST, "/api/v1/series").hasAuthority("admin")
                    .requestMatchers(HttpMethod.GET, "/api.v1/series").hasAuthority("user")
                    .requestMatchers(HttpMethod.GET, "/api.v1/series/seasons").hasAuthority("user")
                    .requestMatchers(HttpMethod.GET, "/api.v1/series/episodes").hasAuthority("user")
                    .anyRequest().authenticated()//;
                )
                .formLogin(login -> login
                        .loginPage("/signIn")  // Thymeleaf sign-in page
                        .loginProcessingUrl("/signIn")
                        .usernameParameter("email") // Tell Spring Security to use 'email' instead of 'username'
                        .passwordParameter("password")// This is the form's action
                        .defaultSuccessUrl("/dashboard", true) // Redirect to dashboard if login is successful
                        .failureUrl("/signIn?error=true") // Show error message if login fails
                        .permitAll()
                )
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/signIn")
                    )
        .httpBasic(Customizer.withDefaults());

//        })


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();

    }
}
