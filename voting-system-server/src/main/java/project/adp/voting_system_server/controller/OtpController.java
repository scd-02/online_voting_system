package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public String validateOtp(@PathVariable String aadharNumber, @PathVariable String otp) {
        try {
            if(otpService.validateOtp(aadharNumber, otp)){
                if(userRepository.findById(aadharNumber).isPresent()){
                    return "exists";
                }else{
                    return "new";
                }
            } else{
                return "wrong";
            }
        } catch (Exception e) {
            return e.toString(); // Return false if validation fails
        }
    }
}
