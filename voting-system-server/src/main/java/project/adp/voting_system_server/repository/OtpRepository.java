package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.adp.voting_system_server.model.Otp;

import java.time.LocalDateTime;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    // Delete OTPs older than a specified time
    void deleteByTimeCreatedBefore(LocalDateTime timeCreated);

    // Find OTP by aadhar number
    Otp findByAadharNumber(String aadharNumber);
}
