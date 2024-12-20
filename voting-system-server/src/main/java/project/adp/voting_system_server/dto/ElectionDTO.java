package project.adp.voting_system_server.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.repository.VoteRepository;

public class ElectionDTO {

    private Long id;
    private String name;
    private String state;
    private List<String> eligiblePartys;
    private boolean active;

    // New field to store the vote counts per party
    private Map<String, Long> partyVotes = new HashMap<>();

    // New field for creation date
    private LocalDateTime creationDate;

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

    public Map<String, Long> getPartyVotes() {
        return partyVotes;
    }

    public void setPartyVotes(Map<String, Long> partyVotes) {
        this.partyVotes = partyVotes;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    // Optionally, you can add a method to convert from the Election entity to
    // ElectionDTO
    public static ElectionDTO fromEntity(Election election) {
        ElectionDTO dto = new ElectionDTO(
                election.getId(),
                election.getName(),
                election.getState(),
                election.getEligiblePartys(),
                election.isActive());
        dto.setCreationDate(election.getCreationDate());
        return dto;
    }

    // New method: fromEntityWithVotes
    // This one requires a VoteRepository to gather party vote counts
    public static ElectionDTO fromEntityWithVotes(Election election, VoteRepository voteRepository) {

        // Create a basic DTO
        ElectionDTO dto = fromEntity(election);

        // Build a map of party -> vote count
        Map<String, Long> partyVotesMap = new HashMap<>();
        for (String partyId : election.getEligiblePartys()) {
            Long count = voteRepository.countVotesByElectionIdAndPartyId(election.getId(), partyId);
            partyVotesMap.put(partyId, count == null ? 0L : count);
        }
        dto.setPartyVotes(partyVotesMap);

        return dto;
    }

    // Optionally, you can add a method to convert back to the entity, if needed
    public Election toEntity() {
        Election election = new Election();
        election.setId(this.id);
        election.setName(this.name);
        election.setState(this.state);
        election.setEligiblePartys(this.eligiblePartys);
        election.setActive(this.active);
        // creationDate is set automatically in the entity's @PrePersist
        return election;
    }
}
