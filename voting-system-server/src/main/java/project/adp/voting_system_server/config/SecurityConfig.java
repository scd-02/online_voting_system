package project.adp.voting_system_server.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Enable CORS with the specified configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // Disable CSRF as it's not needed for APIs that are stateless or use tokens
                .csrf(csrf -> csrf.disable())

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Permit all OPTIONS requests for CORS preflight
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public endpoints
                        .requestMatchers("/otp/**").permitAll()

                        // Authenticated endpoints
                        .requestMatchers("/register/**", "/users/**", "/auth/**", "/election/getElectionsByIds").authenticated()

                        // Role-based access
                        .requestMatchers("/admin/**", "/election/**", "/candidate/**", "/party/**").hasAuthority("ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )

                // Configure form login
                .formLogin(form -> form
                        .loginPage("http://localhost:3000/login") // Redirect to frontend login page
                        .permitAll()
                )

                // Configure logout behavior
                .logout(logout -> logout
                        .logoutUrl("/auth/logout") // Define the logout endpoint
                        .logoutSuccessHandler(customLogoutSuccessHandler()) // Set custom logout success handler
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Remove session cookies
                )

                // Enable HTTP Basic Authentication
                .httpBasic(Customizer.withDefaults())

                // Configure session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                )

                // Exception handling
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler())
                )

                .build();
    }

    // Custom LogoutSuccessHandler to return 200 OK without redirect
    @Bean
    public LogoutSuccessHandler customLogoutSuccessHandler() {
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(
                    jakarta.servlet.http.HttpServletRequest request,
                    jakarta.servlet.http.HttpServletResponse response,
                    Authentication authentication) throws IOException {

                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().flush();
            }
        };
    }

    // Define CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Specify the allowed origin
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        
        // Specify allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Specify allowed headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Allow credentials (cookies, authorization headers)
        configuration.setAllowCredentials(true);
        
        // Apply CORS configuration to all paths
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    // Custom Access Denied Handler
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access Denied");
        };
    }
}