package project.adp.voting_system_server.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.adp.voting_system_server.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/profile")
    public Map<String, String> getUserProfile() {
        // Call the method to fetch current user profile
        return authenticationService.getCurrentUserProfile();
    }
}
