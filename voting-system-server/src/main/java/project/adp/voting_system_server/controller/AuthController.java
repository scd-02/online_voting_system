package project.adp.voting_system_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.model.Admin;
import project.adp.voting_system_server.service.UserService;
import project.adp.voting_system_server.service.AdminService;
import project.adp.voting_system_server.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/checkAadhaar/{aadhaar}")
    public ResponseEntity<Map<String, Object>> checkAadhaar(@PathVariable String aadhaar) {
        // Initialize response map
        Map<String, Object> response = new HashMap<>();

        // Check if user exists in the User table
        User user = userService.getUserByAadhaarNumber(aadhaar);

        if (user != null) {
            // User exists, now check if the user is an admin
            Admin admin = adminService.getAdminById(aadhaar); // Check if Aadhaar exists in Admin table
            // String role = (admin != null) ? "admin" : "voter"; // If admin exists, assign
            // admin role, else voter
            if (admin != null) {
                response.put("role", "admin"); // If admin found, return admin role
            } else {
                response.put("role", "voter"); // If admin not found, return voter role
            }
            // Add user details and role to response
            response.put("exists", true);
            response.put("aadhaarNumber", user.getAadhaarNumber());
            // response.put("role", role); // Add role (admin/voter)
            response.put("message", "User found");

            return ResponseEntity.ok(response);
        } else {
            // User does not exist
            response.put("exists", false);
            response.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/profile")
    public Map<String, String> getUserProfile() {
        // Call the method to fetch current user profile
        return authenticationService.getCurrentUserProfile();
    }
}
