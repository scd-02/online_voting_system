package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.repository.PartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PartyService {

    private final PartyRepository partyRepository;

    @Autowired
    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    // Create or Update a party
    public Party saveParty(Party party) {
        return partyRepository.save(party);
    }

    // Get all parties
    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    // Get a party by ID
    public Optional<Party> getPartyById(Long id) {
        return partyRepository.findById(id);
    }

    // Delete a party
    public void deleteParty(Long id) {
        partyRepository.deleteById(id);
    }
}
