package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.repository.CandidateRepository;
import project.adp.voting_system_server.repository.PartyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PartyRepository partyRepository;

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> getCandidateByAadhaarNumber(String aadhaarNumber) {
        return candidateRepository.findById(aadhaarNumber);
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(String aadhaarNumber) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(aadhaarNumber);
        if (!candidateOpt.isPresent()) {
            throw new EntityNotFoundException("Candidate not found with Aadhaar number: " + aadhaarNumber);
        }

        Candidate candidate = candidateOpt.get();
        Optional<Party> partyOpt = partyRepository.findByLeader(candidate);
        if (partyOpt.isPresent()) {
            throw new IllegalStateException(
                    "Cannot delete candidate who is a party leader. Change the leader or delete the party first.");
        }

        candidateRepository.delete(candidate);
    }

    public void updateCandidate(String aadhaarNumber, String partyName) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(aadhaarNumber);
        if (!candidateOpt.isPresent()) {
            throw new EntityNotFoundException("Candidate not found with Aadhaar number: " + aadhaarNumber);
        }

        Candidate candidate = candidateOpt.get();

        Optional<Party> partyOpt = partyRepository.findById(partyName);
        if (!partyOpt.isPresent()) {
            throw new EntityNotFoundException("Party not found with name: " + partyName);
        }

        if (candidate.getParty().getLeader().equals(candidate)) {
            throw new IllegalStateException("Cannot update party of a candidate who is a party leader.");
        }

        Party party = partyOpt.get();
        candidate.setParty(party);
        candidateRepository.save(candidate);
    }

    public boolean partyExists(Party party) {
        return partyRepository.existsById(party.getName());
    }
}
