package com.upiiz.logs.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir acceso a Swagger sin autenticación
                        .anyRequest().authenticated()
                )
                .csrf().disable()
                .cors().and() // Habilitar CORS
                .build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir tu dominio Angular
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("admin1234")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails user = User.withUsername("user")
                .password("user1234")
                .authorities("READ")
                .build();

        UserDetails moderator = User.withUsername("moderator")
                .password("moderator1234")
                .authorities("READ", "UPDATE")
                .build();

        UserDetails editor = User.withUsername("editor")
                .password("editor1234")
                .authorities("UPDATE")
                .build();

        UserDetails developer = User.withUsername("developer")
                .password("developer1234")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails analyst = User.withUsername("analyst")
                .password("analyst1234")
                .authorities("READ", "DELETE")
                .build();

        return new InMemoryUserDetailsManager(admin, user, moderator, editor, developer, analyst);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}