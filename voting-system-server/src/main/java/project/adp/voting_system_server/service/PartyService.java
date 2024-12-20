package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.repository.CandidateRepository;
import project.adp.voting_system_server.repository.ElectionRepository;
import project.adp.voting_system_server.repository.PartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ElectionRepository electionRepository;

    // Create or Update a party
    public Party saveParty(Party party) {
        return partyRepository.save(party);
    }

    // Get all parties
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    // Get a party by name
    public Optional<Party> getPartyByName(String name) {
        return partyRepository.findById(name);
    }

    // Delete a party
    public void deleteParty(String name) {
        // Find the party
        Party party = partyRepository.findById(name)
                .orElseThrow(() -> new EntityNotFoundException("Party not found with name: " + name));

        // Set leader to null
        party.setLeader(null);
        partyRepository.save(party);

        // Delete all candidates belonging to the party
        List<Candidate> candidates = candidateRepository.findByParty(party);
        candidateRepository.deleteAll(candidates);

        // Delete all record of party from any election
        electionRepository.deleteByPartyName(name);

        // Delete the party
        partyRepository.delete(party);
    }

    // Method to set the leader of a party
    public Party setPartyLeader(String partyName, String leaderAadhaarNumber) {
        Optional<Party> partyOpt = partyRepository.findById(partyName);
        Optional<Candidate> leaderOpt = candidateRepository.findById(leaderAadhaarNumber);

        if (partyOpt.isPresent() && leaderOpt.isPresent()) {
            Party party = partyOpt.get();
            Candidate leader = leaderOpt.get();

            // Ensure the leader belongs to the party
            if (leader.getParty() != null && leader.getParty().getName().equals(partyName)) {
                party.setLeader(leader);
                return partyRepository.save(party);
            } else {
                throw new RuntimeException("Leader does not belong to the specified party.");
            }
        } else {
            throw new RuntimeException("Party or Leader not found.");
        }
    }

    public boolean partyInOngoingElection(String partyName){
        return electionRepository.existsPartyInElections(partyName);
    }

    // /**
    //  * Deletes a party and its associated leader candidate.
    //  *
    //  * @param name The name of the party to delete.
    //  */
    // @Transactional
    // public void deletePartyAndLeader(String name) {
    //     Optional<Party> partyOpt = partyRepository.findById(name);
    //     if (partyOpt.isPresent()) {
    //         Party party = partyOpt.get();
    //         Candidate leader = party.getLeader();
    //         if (leader != null) {
    //             candidateRepository.delete(leader);
    //         }
    //         partyRepository.delete(party);
    //     } else {
    //         throw new EntityNotFoundException("Party not found with name: " + name);
    //     }
    // }
}
