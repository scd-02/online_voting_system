package project.adp.voting_system_server.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.config.TwilioConfig;
import project.adp.voting_system_server.repository.PersonRepository;

import java.util.Random;

@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    @Autowired
    private PersonRepository personRepository; // Use PersonRepository instead of UserRepository

    @Autowired
    private OtpService otpService;

    // Send OTP and save in database
    public String sendOtp(String aadhaarNumber) {
        // Fetch the person's phone number based on the aadhaarNumber from
        // PersonRepository
        String phoneNumber = personRepository.findById(aadhaarNumber)
                .map(person -> person.getMobileNumber()) // Get mobile number if present
                .orElseThrow(() -> new RuntimeException("Phone number not found for the provided Aadhaar number"));

        // Generate OTP
        String otp = generateOtp();

        // Save OTP in the database (OtpService is responsible for this)
        otpService.saveOtp(aadhaarNumber, otp);

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
            // Initialize Twilio with Account SID and Auth Token
            Twilio.init(twilioConfig.getAccoundSid(), twilioConfig.getAuthToken());

            // Send OTP message
            Message.creator(
                    new PhoneNumber(phoneNumber), // "To" phone number
                    new PhoneNumber(twilioConfig.getPhoneNumber()), // "From" phone number (Twilio number)
                    "Your OTP for verification is: " + otp).create();
        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP via Twilio: " + e.getMessage());
        }
    }
}
