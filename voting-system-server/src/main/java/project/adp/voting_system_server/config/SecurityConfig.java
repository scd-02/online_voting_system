package project.adp.voting_system_server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())// turn off the verification of csrf
                // all requests need to be authenticated
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())// set the login page (to default)
                .httpBasic(Customizer.withDefaults())// set the basic http call authentication to default
                // set the session management to stateless
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // to hard code the authtentication

    // @Bean
    // public UserDetailsService userDetailsService() {
    // // tell how to authenticate the users

    // UserDetails user1 = User
    // .withDefaultPasswordEncoder()
    // .username("admin1")
    // .password("admin1")
    // .roles("ADMIN")
    // .build();

    // UserDetails user2 = User
    // .withDefaultPasswordEncoder()
    // .username("user1")
    // .password("user1")
    // .roles("USER")
    // .build();

    // return new InMemoryUserDetailsManager(user1, user2);
    // }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // tell how to authenticate the users
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }
}

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import
// org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;

// import java.util.Arrays;

// @Configuration
// public class SecurityConfig {

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// // Configure authorization: require authentication but no role checks
// .authorizeHttpRequests(authz -> authz
// .requestMatchers("/auth/**").permitAll()
// // .requestMatchers("/users/**").authenticated() // Allow only authenticated
// users
// // .requestMatchers("/admin/**").authenticated() // Allow only authenticated
// users
// .anyRequest().permitAll()) // All other endpoints are publicly accessible
// // .formLogin(form -> form
// // .loginPage("http://localhost:3000/login") // External login page in
// Next.js
// // .loginProcessingUrl("/users/login") // Login processing URL in Spring Boot
// // .defaultSuccessUrl("/home", true) // Redirect to /home after successful
// login
// // .failureUrl("http://localhost:3000/login?error=true")) // Redirect on
// login failure
// // .logout(logout -> logout
// // .logoutUrl("/logout")
// // .logoutSuccessUrl("http://localhost:3000/login?logout=true")) // Redirect
// after logout
// // .sessionManagement(session -> session
// // .invalidSessionUrl("http://localhost:3000/session-expired") // Redirect
// after session expiration
// // .maximumSessions(1) // Limit to one session per user if needed
// // .expiredUrl("http://localhost:3000/session-expired") // Redirect to
// session expired page
// // Updated CSRF configuration
// .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (enable in
// production if needed)
// // Updated CORS configuration
// .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Set
// CORS configuration source

// return http.build();
// }

// @Bean
// public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder(); // Password encoder for secure password
// hashing
// }

// // Global CORS configuration
// @Bean
// public CorsConfigurationSource corsConfigurationSource() {
// CorsConfiguration corsConfiguration = new CorsConfiguration();
// corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
// // Allow Next.js frontend
// corsConfiguration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(),
// HttpMethod.POST.name(),
// HttpMethod.PUT.name(), HttpMethod.DELETE.name())); // Set allowed methods
// corsConfiguration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
// corsConfiguration.setAllowCredentials(true); // Allow credentials (cookies,
// authorization headers, etc.)

// org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new
// org.springframework.web.cors.UrlBasedCorsConfigurationSource();
// source.registerCorsConfiguration("/**", corsConfiguration); // Apply CORS to
// all paths
// return source;
// }
// }
