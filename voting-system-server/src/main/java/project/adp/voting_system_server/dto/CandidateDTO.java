package project.adp.voting_system_server.dto;

import project.adp.voting_system_server.model.Candidate;

public class CandidateDTO {
    private String aadhaarNumber;
    private String partyName;

    // Default constructor
    public CandidateDTO() {
    }

    // Constructor to create DTO from Candidate entity
    public CandidateDTO(Candidate candidate) {
        this.aadhaarNumber = candidate.getAadhaarNumber();
        this.partyName = candidate.getParty() != null ? candidate.getParty().getName() : null;
    }

    // Getters and Setters
    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}
