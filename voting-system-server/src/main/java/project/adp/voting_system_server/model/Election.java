package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(indexes = @Index(name = "idx_state", columnList = "state")) // Updated index for state
public class Election {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String state; // Updated field to state

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

    public String getState() {
        return state; // Updated getter
    }

    public void setState(String state) {
        this.state = state; // Updated setter
    }

    public List<String> getEligiblePartys() {
        return eligiblePartys;
    }

    public void setEligiblePartys(List<String> eligiblePartys) {
        this.eligiblePartys = eligiblePartys;
    }
}
