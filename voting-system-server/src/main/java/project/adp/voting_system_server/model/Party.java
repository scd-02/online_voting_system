package project.adp.voting_system_server.model;

import jakarta.persistence.*;

@Entity
@Table(name = "party", indexes = {
        @Index(name = "idx_party_state", columnList = "state") // Updated index name and column
})
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;

    @OneToOne
    @JoinColumn(name = "leader_id") // This will create a foreign key column named leader_id in the party table
    private Candidate leader;

    private String agenda;

    @Column(nullable = false)
    private String state; // Renamed field to store the state of the party

    // Default constructor
    public Party() {
    }

    // Constructor with parameters
    public Party(String name, String symbol, Candidate leader, String agenda, String state) {
        this.name = name;
        this.symbol = symbol;
        this.leader = leader;
        this.agenda = agenda;
        this.state = state; // Initialize the state
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
