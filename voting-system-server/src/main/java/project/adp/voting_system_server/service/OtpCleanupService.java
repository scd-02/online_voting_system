package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.adp.voting_system_server.repository.OtpRepository;

import java.time.LocalDateTime;

@Service
public class OtpCleanupService {

    @Autowired
    private OtpRepository otpRepository;

    // Run every 2 minutes
    @Scheduled(cron = "0 */2 * * * *") // Every 2 minutes
    @Transactional // Add @Transactional to ensure this method runs within a transaction
    public void deleteExpiredOtps() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusMinutes(2); // OTPs expire after 2 minutes
        otpRepository.deleteByTimeCreatedBefore(expirationThreshold);
        System.out.println("Expired OTPs deleted at: " + LocalDateTime.now());
    }
}
