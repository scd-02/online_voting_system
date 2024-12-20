package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
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

    @PutMapping("/{aadhaarNumber}")
    public ResponseEntity<Object> updateCandidate(@PathVariable String aadhaarNumber, @RequestParam String partyName) {
        try {
            CandidateDTO updatedCandidate = candidateService.updateCandidate(aadhaarNumber, partyName);
            return ResponseEntity.ok(updatedCandidate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the candidate.");
        }
    }

    @GetMapping("/{aadhaarNumber}")
    public ResponseEntity<Candidate> getCandidateByAadhaarNumber(@PathVariable String aadhaarNumber) {
        return candidateService.getCandidateByAadhaarNumber(aadhaarNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCandidate(@RequestBody Candidate candidate) {
        if (!candidateService.partyExists(candidate.getParty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The specified party does not exist. Please create the party first.");
        }
        try {
            Candidate savedCandidate = candidateService.saveCandidate(candidate);
            return ResponseEntity.status(HttpStatus.CREATED).body(new CandidateDTO(savedCandidate));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the candidate.");
        }
    }

    @DeleteMapping("/{aadhaarNumber}")
    public ResponseEntity<Object> deleteCandidate(@PathVariable String aadhaarNumber) {
        try {
            candidateService.deleteCandidate(aadhaarNumber);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the candidate.");
        }
    }
}
