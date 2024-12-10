package project.adp.voting_system_server.model;

import jakarta.persistence.*;

@Entity
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;

    // The leader is now a reference to the Candidate entity
    // @ManyToOne

    @OneToOne
    @JoinColumn(name = "leader_id") // This will create a foreign key column named leader_id in the party table
    private Candidate leader;

    private String agenda; // Changed attribute name to 'agenda'

    // Default constructor
    public Party() {
    }

    // Constructor with parameters
    public Party(String name, String symbol, Candidate leader, String agenda) {
        this.name = name;
        this.symbol = symbol;
        this.leader = leader; // Leader is now a Candidate object
        this.agenda = agenda; // Updated parameter
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
        return leader; // Updated getter to return the Candidate object
    }

    public void setLeader(Candidate leader) {
        this.leader = leader; // Updated setter to accept a Candidate object
    }

    public String getAgenda() {
        return agenda; // Updated getter
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda; // Updated setter
    }
}
