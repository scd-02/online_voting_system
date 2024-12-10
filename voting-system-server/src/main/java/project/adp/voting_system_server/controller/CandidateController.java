package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.service.CandidateService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    // Get all candidates
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    // Get a candidate by ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> candidate = candidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new candidate
    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(savedCandidate);
    }

    // Update a candidate
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate candidate) {
        if (!candidateService.getCandidateById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        candidate.setId(id); // Ensure the ID is not changed
        Candidate updatedCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(updatedCandidate);
    }

    // Delete a candidate
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        if (!candidateService.getCandidateById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

    // Get candidates by party_id
    @GetMapping("/party/{partyId}")
    public List<Candidate> getCandidatesByPartyId(@PathVariable Long partyId) {
        return candidateService.getCandidatesByPartyId(partyId);
    }

    // Get candidates by aadhaarNumber
    @GetMapping("/aadhaar/{aadhaarNumber}")
    public List<Candidate> getCandidatesByAadhaarNumber(@PathVariable String aadhaarNumber) {
        return candidateService.getCandidatesByAadhaarNumber(aadhaarNumber);
    }
}
