package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.model.Otp;
import project.adp.voting_system_server.repository.OtpRepository;

import java.time.LocalDateTime;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    // Generate and save OTP
    public void saveOtp(String aadharNumber, String otp) {
        Otp otpEntity = new Otp();
        otpEntity.setAadharNumber(aadharNumber);
        otpEntity.setOtp(otp);
        otpEntity.setTimeCreated(LocalDateTime.now());
        otpRepository.save(otpEntity);
    }

    // Validate OTP
    public boolean validateOtp(String aadharNumber, String otp) {
        Otp otpEntity = otpRepository.findByAadharNumber(aadharNumber);

        // If no OTP exists for this aadhar number or OTP is invalid
        if (otpEntity == null || !otpEntity.getOtp().equals(otp)) {
            return false;
        }

        // Check if OTP is expired (2 minutes TTL)
        if (otpEntity.getTimeCreated().isBefore(LocalDateTime.now().minusMinutes(2))) {
            otpRepository.deleteById(aadharNumber); // Remove expired OTP
            return false;
        }

        // OTP is valid
        otpRepository.deleteById(aadharNumber); // Remove used OTP
        return true;
    }
}
