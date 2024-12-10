package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_region", columnList = "region"))
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String region;

    //aadhaarNumber
    @ElementCollection
    @CollectionTable(name = "election_eligible_voters", joinColumns = @JoinColumn(name = "election_id"))
    @Column(name = "voter_id")
    private List<String> eligibleVoters;

    @ElementCollection
    @CollectionTable(name = "election_eligible_partys", joinColumns = @JoinColumn(name = "election_id"))
    @Column(name = "party_id")
    private List<String> eligiblePartys;

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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<String> getEligibleVoters() {
        return eligibleVoters;
    }

    public void setEligibleVoters(List<String> eligibleVoters) {
        this.eligibleVoters = eligibleVoters;
    }

    public List<String> getEligiblePartys() {
        return eligiblePartys;
    }

    public void setEligiblePartys(List<String> eligiblePartys) {
        this.eligiblePartys = eligiblePartys;
    }
}
