package project.adp.voting_system_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(indexes = { @Index(name = "idx_party_id", columnList = "party_id") })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "aadhaarNumber")
public class Candidate {

    @Id
    private String aadhaarNumber; // Changed userId to aadhaarNumber

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    // Default constructor
    public Candidate() {
    }

    // Constructor with parameters
    public Candidate(String aadhaarNumber, Party party) {
        this.aadhaarNumber = aadhaarNumber;
        this.party = party;
    }

    // Getters and Setters
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", party=" + party +
                '}';
    }
}
