package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // Step 4: Create or retrieve the session and return its ID
        String sessionId = request.getSession(true).getId();
        // true ensures a new session is created if it doesn't exist

        // Step 5: Return both sessionId and role in a map
        Map<String, String> response = new HashMap<>();
        response.put("sessionId", sessionId);
        response.put("role", role);

        return response;
    }
}
