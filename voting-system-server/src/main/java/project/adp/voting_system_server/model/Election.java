package project.adp.voting_system_server.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "party_name")
    private List<String> eligiblePartys;

    @Column(nullable = false, columnDefinition = "boolean default true") // Default value set to true
    private boolean active; // New field to indicate if the election is active

    // New field for creation date
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
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

    public boolean isActive() {
        return active; // Getter for active field
    }

    public void setActive(boolean active) {
        this.active = active; // Setter for active field
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
