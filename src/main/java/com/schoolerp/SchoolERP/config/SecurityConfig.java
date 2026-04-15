package com.schoolerp.SchoolERP.config;

import com.schoolerp.SchoolERP.security.CustomUserDetailsService;
import com.schoolerp.SchoolERP.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AUTH PROVIDER
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // CORS CONFIGURATION
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("*")); // For production, replace * with your Vercel URL
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type"));
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // SECURITY FILTER CHAIN
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authProvider())

                .authorizeHttpRequests(auth -> auth

                        /* --------- SWAGGER UI (Allow Completely) --------- */
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml"
                        ).permitAll()

                        /* --------- FIRST ADMIN CREATION & AUTH --------- */
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()

                        /* --------- ADMIN-ONLY --------- */
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        /* --------- PROTECTED MODULES (Teacher/Staff Login Required) --------- */
                        .requestMatchers("/api/students/**").authenticated()
                        .requestMatchers("/api/teachers/**").authenticated()
                        .requestMatchers("/api/staffs/**").authenticated()
                        .requestMatchers("/api/classes/**").authenticated()
                        .requestMatchers("/api/subjects/**").authenticated()
                        .requestMatchers("/api/attendance/**").authenticated()
                        .requestMatchers("/api/salary/**").authenticated()
                        .requestMatchers("/api/books/**").authenticated()
                        .requestMatchers("/api/library/**").authenticated()
                        .requestMatchers("/api/feestructures/**").authenticated()
                        .requestMatchers("/api/feepayments/**").authenticated()

                        /* --------- Exams and Marks --------- */
                        .requestMatchers("/api/exams/**").authenticated()
                        .requestMatchers("/api/exam-subjects/**").authenticated()
                        .requestMatchers("/api/marks/**").authenticated()

                        /* --------- Anything else --------- */
                        .anyRequest().authenticated()
                )

                /* --------- JWT FILTER --------- */
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
