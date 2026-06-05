package com.E_COMM.Dream_shop.Security.config;

import com.E_COMM.Dream_shop.Security.JWT.AuthTokenFilter;
import com.E_COMM.Dream_shop.Security.JWT.JwtAuthEntryPoint;
import com.E_COMM.Dream_shop.Security.JWT.JwtUtils;
import com.E_COMM.Dream_shop.Security.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ShopConfig {

    private final ShopUserDetailsService  shopUserDetailsService;
    private final JwtAuthEntryPoint  jwtAuthEntryPoint;
    private final JwtUtils jwtUtils;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(
            JwtUtils jwtUtils,
            ShopUserDetailsService shopUserDetailsService) {

        return new AuthTokenFilter(jwtUtils, shopUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration  authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF for stateless REST APIs
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configure Exception Handling for Unauthorized Access
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthEntryPoint))

                // 3. Make Session Management Stateless (No Sessions Stored)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Configure Endpoint Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(apiPrefix + "/auth/**", apiPrefix + "/User/**").permitAll() // Public routes
                        .anyRequest().authenticated() // Secure everything else
                );


        // 6. Inject your custom JWT AuthTokenFilter before the standard UsernamePassword Filter
        http.addFilterBefore(authTokenFilter(jwtUtils,shopUserDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
