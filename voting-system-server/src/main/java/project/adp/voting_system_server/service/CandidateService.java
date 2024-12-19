package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.repository.CandidateRepository;

import java.util.List;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateByAadhaarNumber(String aadhaarNumber) {
        return candidateRepository.findById(aadhaarNumber).orElse(null);
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(String aadhaarNumber) {
        candidateRepository.deleteById(aadhaarNumber);
    }
}
