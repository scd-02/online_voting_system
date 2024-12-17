package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import project.adp.voting_system_server.model.Admin;
import project.adp.voting_system_server.repository.AdminRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class AuthenticationService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Authenticate a user without a username and password, and return the session
     * ID and role.
     *
     * @param userId  The ID of the user to authenticate.
     * @param request The HttpServletRequest to create/retrieve the session.
     * @return A map containing session ID and user role.
     */
    public Map<String, String> authenticateAndReturnSession(String userId, HttpServletRequest request) {
        // Step 1: Check the role of the user (admin or user)
        Admin admin = adminRepository.findByUserId(userId);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String role;

        if (admin == null) {
            // A regular user
            authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
            role = "USER";
        } else {
            // An admin user
            authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
            role = "ADMIN";
        }

        // Step 2: Create an Authentication token with the user ID and roles
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, null,
                authorities);

        // Step 3: Set the Authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // Step 4: Create or retrieve the session and set the security context
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
        // Step 4: Create or retrieve the session and return its ID
        String sessionId = request.getSession(true).getId();
        // true ensures a new session is created if it doesn't exist

        // Step 5: Return both sessionId and role in a map
        Map<String, String> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("role", role);

        return response;
    }

    /**
     * Get the profile details of the currently logged-in user.
     *
     * @return A map containing user details like user ID and role.
     */
    public Map<String, String> getCurrentUserProfile() {
        Map<String, String> userProfile = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Log the authentication details for debugging purposes
        if (authentication == null) {
            System.out.println("Authentication is null");
        } else {
            System.out.println("Authentication principal: " + authentication.getPrincipal());
            System.out.println("Authentication authorities: " + authentication.getAuthorities());
            System.out.println("Authentication details: " + authentication.getDetails());
            System.out.println("Authentication name: " + authentication.getName());
        }

        // If authentication is an anonymous token, handle it properly
        if (authentication instanceof AnonymousAuthenticationToken) {
            // Handle anonymous user case (not authenticated)
            userProfile.put("error", "User is not authenticated");
            return userProfile;
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            // Step 2: Retrieve user ID (or username) and authorities from the
            // Authentication token
            String userId = (String) authentication.getPrincipal(); // Assuming user ID is the principal

            // Step 3: Get the user's role(s) (authorities)
            String role = "USER"; // Default to USER
            if (!authentication.getAuthorities().isEmpty()) {
                // Get the first authority (role)
                role = authentication.getAuthorities().iterator().next().getAuthority();
            }

            // Step 4: Populate the user profile map with details
            userProfile.put("userId", userId);
            userProfile.put("role", role);
        }

        return userProfile;
    }
}
