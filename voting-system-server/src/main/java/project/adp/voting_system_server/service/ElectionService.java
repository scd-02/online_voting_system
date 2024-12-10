package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.repository.ElectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionService {

    private final ElectionRepository electionRepository;

    @Autowired
    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Optional<Election> getElectionById(Long id) {
        return electionRepository.findById(id);
    }

    public List<Election> getElectionsByRegion(String region) {
        return electionRepository.findByRegion(region);
    }

    public Election createElection(Election election) {
        return electionRepository.save(election);
    }

    public Election updateElection(Long id, Election electionDetails) {
        return electionRepository.findById(id).map(election -> {
            election.setName(electionDetails.getName());
            election.setRegion(electionDetails.getRegion());
            election.setEligibleVoters(electionDetails.getEligibleVoters());
            election.setEligiblePartys(electionDetails.getEligiblePartys());
            return electionRepository.save(election);
        }).orElseThrow(() -> new RuntimeException("Election not found with id " + id));
    }

    public void deleteElection(Long id) {
        electionRepository.deleteById(id);
    }
}
