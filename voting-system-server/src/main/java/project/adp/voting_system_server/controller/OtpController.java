package project.adp.voting_system_server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import project.adp.voting_system_server.service.AuthenticationService;
import project.adp.voting_system_server.service.OtpService;
import project.adp.voting_system_server.service.SmsService;
import project.adp.voting_system_server.repository.UserRepository;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    // Endpoint to send OTP (fetch phone number from the UserService based on
    // aadharNumber)
    @PostMapping("/send/{aadharNumber}")
    public String sendOtp(@PathVariable String aadharNumber) {
        try {
            // Send OTP and store in the database, fetching the phone number internally
            smsService.sendOtp(aadharNumber);
            return "OTP sent successfully to the registered phone number.";
        } catch (Exception e) {
            return "Error occurred while sending OTP: " + e.getMessage();
        }
    }

    @PostMapping("/validate/{aadharNumber}/{otp}")
    public ResponseEntity<Map<String, String>> validateOtp(@PathVariable String aadharNumber, @PathVariable String otp,
            HttpServletRequest request) {
        try {
            // Validate OTP
            if (otpService.validateOtp(aadharNumber, otp)) {
                Map<String, String> response = new HashMap<>();

                // Authenticate user and get the session ID
                Map<String, String> authenticationResponse = authenticationService
                        .authenticateAndReturnSession(aadharNumber, request);

                String sessionId = authenticationResponse.get("sessionId");
                response.put("sessionId", sessionId);

                // Check if user exists in the repository
                if (userRepository.findById(aadharNumber).isPresent()) {
                    // Existing user
                    response.put("user", "exist");
                } else {
                    // New user
                    response.put("user", "new");
                }

                // Return response with HTTP 200 OK
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } else {
                // Invalid OTP
                Map<String, String> response = new HashMap<>();
                response.put("user", "wrong"); // Invalid OTP response

                // Return response with HTTP 400 Bad Request
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            // Handle exceptions and return the error message
            Map<String, String> response = new HashMap<>();
            response.put("error", e.toString());

            // Return response with HTTP 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
