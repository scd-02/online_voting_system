package project.adp.voting_system_server.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.config.TwilioConfig;
import project.adp.voting_system_server.repository.UserRepository;

import java.util.Random;

@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private UserRepository userRepository; // Assuming UserRepository fetches user data including phone number

    @Autowired
    private OtpService otpService;

    // Send OTP and save in database
    public String sendOtp(String aadharNumber) {
        // Fetch the user's phone number based on the aadharNumber from UserRepository
        String phoneNumber = userRepository.findByAadhaarNumber(aadharNumber).getMobileNumber();

        if (phoneNumber == null) {
            throw new RuntimeException("Phone number not found for the provided Aadhar number");
        }

        // Generate OTP
        String otp = generateOtp();

        // Save OTP in the database (OtpService is responsible for this)
        otpService.saveOtp(aadharNumber, otp);

        // Send OTP via Twilio
        sendOtpViaTwilio(phoneNumber, otp);

        return otp; // Return the OTP (you might not want to return it in production)
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    // Send OTP to the phone number via Twilio API
    private void sendOtpViaTwilio(String phoneNumber, String otp) {
        try {
            Message.creator(
                    new PhoneNumber(phoneNumber), // "To" phone number
                    new PhoneNumber(twilioConfig.getPhoneNumber()), // "From" phone number (Twilio number)
                    "Your OTP for verification is: " + otp).create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP via Twilio: " + e.getMessage());
        }
    }
}
