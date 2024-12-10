package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_election_party", columnList = "election_id, party_id")
})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voter_id", nullable = false)
    private String voterId;

    @Column(name = "party_id", nullable = false)
    private String partyId;

    @Column(name = "election_id", nullable = false)
    private Long electionId;

    @Column(name = "time_of_vote", nullable = false)
    private LocalDateTime timeOfVote;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public Long getElectionId() {
        return electionId;
    }

    public void setElectionId(Long electionId) {
        this.electionId = electionId;
    }

    public LocalDateTime getTimeOfVote() {
        return timeOfVote;
    }

    public void setTimeOfVote(LocalDateTime timeOfVote) {
        this.timeOfVote = timeOfVote;
    }
}
