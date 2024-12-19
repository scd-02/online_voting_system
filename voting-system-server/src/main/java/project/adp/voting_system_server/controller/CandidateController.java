package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.dto.CandidateDTO;
import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.service.CandidateService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/getAllCandidates")
    public List<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        return candidates.stream()
                .map(CandidateDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{aadhaarNumber}")
    public ResponseEntity<Candidate> getCandidateByAadhaarNumber(@PathVariable String aadhaarNumber) {
        Candidate candidate = candidateService.getCandidateByAadhaarNumber(aadhaarNumber);
        if (candidate != null) {
            return ResponseEntity.ok(candidate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        return ResponseEntity.ok(savedCandidate);
    }

    @DeleteMapping("/{aadhaarNumber}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable String aadhaarNumber) {
        candidateService.deleteCandidate(aadhaarNumber);
        return ResponseEntity.noContent().build();
    }
}
