package project.adp.voting_system_server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "party", indexes = {
        @Index(name = "idx_party_state", columnList = "state") // Updated index name and column
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
public class Party {

    @Id
    private String name; // Changed to use 'name' as the primary key

    @OneToOne
    @JoinColumn(name = "leader_id") // This will create a foreign key column named leader_id in the party table
    @JsonManagedReference
    private Candidate leader;

    private String agenda;

    @Column(nullable = false)
    private String state; // Renamed field to store the state of the party

    // Default constructor
    public Party() {
    }

    // Constructor with parameters
    public Party(String name, Candidate leader, String agenda, String state) {
        this.name = name;
        this.leader = leader;
        this.agenda = agenda;
        this.state = state; // Initialize the state
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Candidate getLeader() {
        return leader;
    }

    public void setLeader(Candidate leader) {
        this.leader = leader;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getState() {
        return state; // Updated getter
    }

    public void setState(String state) {
        this.state = state; // Updated setter
    }
}
