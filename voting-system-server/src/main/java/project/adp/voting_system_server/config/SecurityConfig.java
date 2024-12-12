package project.adp.voting_system_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Configure authorization: require authentication but no role checks
                .authorizeHttpRequests(authz -> authz
                        // .requestMatchers("/users/**").authenticated() // Allow only authenticated users
                        // .requestMatchers("/admin/**").authenticated() // Allow only authenticated users
                        .anyRequest().permitAll()) // All other endpoints are publicly accessible
                .formLogin(form -> form
                        .loginPage("http://localhost:3000/login") // External login page in Next.js
                        .loginProcessingUrl("/perform_login") // Login processing URL in Spring Boot
                        .defaultSuccessUrl("/home", true) // Redirect to /home after successful login
                        .failureUrl("http://localhost:3000/login?error=true")) // Redirect on login failure
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("http://localhost:3000/login?logout=true")) // Redirect after logout
                // Updated CSRF configuration
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (enable in production if needed)
                // Updated CORS configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Set CORS configuration source

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder for secure password hashing
    }

    // Global CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Allow Next.js frontend
        corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.DELETE.name())); // Set allowed methods
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        corsConfiguration.setAllowCredentials(true); // Allow credentials (cookies, authorization headers, etc.)

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Apply CORS to all paths
        return source;
    }
}
