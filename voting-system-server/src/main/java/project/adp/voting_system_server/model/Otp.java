package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
public class Otp {

    @Id
    @Column(name = "aadhar_number", nullable = false, unique = true)
    private String aadharNumber; // Primary key

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "time_created", nullable = false)
    private LocalDateTime timeCreated;

    // Getters and Setters
    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }
}
