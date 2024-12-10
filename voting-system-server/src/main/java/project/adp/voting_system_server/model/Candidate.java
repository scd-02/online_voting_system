package project.adp.voting_system_server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates", indexes = { @Index(name = "idx_party_id", columnList = "party_id") })
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aadhaarNumber; // Changed userId to aadhaarNumber

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber; // Updated getter
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber; // Updated setter
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
