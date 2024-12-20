package project.adp.voting_system_server.dto;

import java.util.List;

import project.adp.voting_system_server.model.Election;

public class ElectionDTO {

    private Long id;
    private String name;
    private String state;
    private List<String> eligiblePartys;
    private boolean active;

    // Default constructor
    public ElectionDTO() {
    }

    // Constructor to initialize fields from Election entity
    public ElectionDTO(Long id, String name, String state, List<String> eligiblePartys, boolean active) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.eligiblePartys = eligiblePartys;
        this.active = active;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getEligiblePartys() {
        return eligiblePartys;
    }

    public void setEligiblePartys(List<String> eligiblePartys) {
        this.eligiblePartys = eligiblePartys;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Optionally, you can add a method to convert from the Election entity to
    // ElectionDTO
    public static ElectionDTO fromEntity(Election election) {
        return new ElectionDTO(
                election.getId(),
                election.getName(),
                election.getState(),
                election.getEligiblePartys(),
                election.isActive());
    }

    // Optionally, you can add a method to convert back to the entity, if needed
    public Election toEntity() {
        Election election = new Election();
        election.setId(this.id);
        election.setName(this.name);
        election.setState(this.state);
        election.setEligiblePartys(this.eligiblePartys);
        election.setActive(this.active);
        return election;
    }
}
