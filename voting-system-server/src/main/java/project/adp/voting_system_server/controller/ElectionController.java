package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.dto.ElectionDTO;
import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.repository.VoteRepository;
import project.adp.voting_system_server.service.ElectionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    // Inject VoteRepository to fetch vote counts
    @Autowired
    private VoteRepository voteRepository;

    // Get all elections
    @GetMapping("/getAllElections")
    public ResponseEntity<List<ElectionDTO>> getAllElections() {
        List<Election> electionEntities = electionService.getAllElections();
        // Use fromEntityWithVotes to return creationDate & partyVotes
        List<ElectionDTO> elections = electionEntities.stream()
                .map(e -> ElectionDTO.fromEntityWithVotes(e, voteRepository))
                .collect(Collectors.toList());
        return ResponseEntity.ok(elections);
    }

    // Get election by ID
    @GetMapping("/{id}")
    public ResponseEntity<ElectionDTO> getElectionById(@PathVariable Long id) {
        return electionService.getElectionById(id)
                .map(e -> ResponseEntity.ok(ElectionDTO.fromEntityWithVotes(e, voteRepository)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get elections by region
    @GetMapping("/region/{region}")
    public ResponseEntity<List<ElectionDTO>> getElectionsByRegion(@PathVariable String region) {
        List<Election> regionElections = electionService.getElectionsByRegion(region);
        List<ElectionDTO> elections = regionElections.stream()
                .map(e -> ElectionDTO.fromEntityWithVotes(e, voteRepository))
                .collect(Collectors.toList());
        return ResponseEntity.ok(elections);
    }

    // Create election
    @PostMapping("/create")
    public ResponseEntity<ElectionDTO> createElection(@RequestBody ElectionDTO electionDTO) {
        Election election = electionDTO.toEntity();
        Election created = electionService.createElection(election);
        // Return DTO with creationDate & partyVotes
        return ResponseEntity.ok(ElectionDTO.fromEntityWithVotes(created, voteRepository));
    }

    // Update election
    @PutMapping("/{id}")
    public ResponseEntity<ElectionDTO> updateElection(@PathVariable Long id, @RequestBody ElectionDTO electionDTO) {
        try {
            Election election = electionDTO.toEntity();
            Election updated = electionService.updateElection(id, election);
            // Return DTO with creationDate & partyVotes
            return ResponseEntity.ok(ElectionDTO.fromEntityWithVotes(updated, voteRepository));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete election
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.noContent().build();
    }

    // Set active status of an election
    @PostMapping("/{id}/status")
    public ResponseEntity<ElectionDTO> setActiveStatus(@PathVariable Long id, @RequestParam boolean active) {
        try {
            Election updatedElection = electionService.setActiveStatus(id, active);
            // Return DTO with creationDate & partyVotes
            return ResponseEntity.ok(ElectionDTO.fromEntityWithVotes(updatedElection, voteRepository));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
