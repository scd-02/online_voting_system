package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.service.OtpService;
import project.adp.voting_system_server.service.SmsService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private OtpService otpService;

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

    // Endpoint to validate OTP
    @GetMapping("/validate/{aadharNumber}/{otp}")
    public boolean validateOtp(@PathVariable String aadharNumber, @PathVariable String otp) {
        try {
            return otpService.validateOtp(aadharNumber, otp); // Validate OTP for the given aadharNumber
        } catch (Exception e) {
            return false; // Return false if validation fails
        }
    }
}
