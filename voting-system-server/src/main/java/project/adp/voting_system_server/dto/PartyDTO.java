package project.adp.voting_system_server.dto;

import project.adp.voting_system_server.model.Party;

public class PartyDTO {
    private String name;
    private String agenda;
    private String state;
    private String leaderAadhaarNumber;

    // Default constructor
    public PartyDTO() {
    }

    // Constructor to create DTO from Party entity
    public PartyDTO(Party party) {
        this.name = party.getName();
        this.agenda = party.getAgenda();
        this.state = party.getState();
        this.leaderAadhaarNumber = party.getLeader() != null ? party.getLeader().getAadhaarNumber() : null;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLeaderAadhaarNumber() {
        return leaderAadhaarNumber;
    }

    public void setLeaderAadhaarNumber(String leaderAadhaarNumber) {
        this.leaderAadhaarNumber = leaderAadhaarNumber;
    }
}
