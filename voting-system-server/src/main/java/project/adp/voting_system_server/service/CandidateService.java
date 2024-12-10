package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.repository.CandidateRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    // Create or Update a candidate
    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    // Get all candidates
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // Get a candidate by ID
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id);
    }

    // Get candidates by party_id
    public List<Candidate> getCandidatesByPartyId(Long partyId) {
        return candidateRepository.findByPartyId(partyId); // Custom query by party_id
    }

    // Get candidates by aadhaarNumber
    public List<Candidate> getCandidatesByAadhaarNumber(String aadhaarNumber) {
        return candidateRepository.findByAadhaarNumber(aadhaarNumber); // Custom query by aadhaarNumber
    }

    // Delete a candidate
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
