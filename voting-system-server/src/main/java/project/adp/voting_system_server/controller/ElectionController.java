package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.dto.ElectionDTO;
import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.service.ElectionService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    // Get all elections
    @GetMapping("/getAllElections")
    public ResponseEntity<List<ElectionDTO>> getAllElections() {
        List<ElectionDTO> elections = electionService.getAllElections().stream()
                .map(ElectionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(elections);
    }

    // Get election by ID
    @GetMapping("/{id}")
    public ResponseEntity<ElectionDTO> getElectionById(@PathVariable Long id) {
        return electionService.getElectionById(id)
                .map(election -> ResponseEntity.ok(ElectionDTO.fromEntity(election)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get elections by region
    @GetMapping("/region/{region}")
    public ResponseEntity<List<ElectionDTO>> getElectionsByRegion(@PathVariable String region) {
        List<ElectionDTO> elections = electionService.getElectionsByRegion(region).stream()
                .map(ElectionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(elections);
    }

    // Create election
    @PostMapping("/create")
    public ResponseEntity<ElectionDTO> createElection(@RequestBody ElectionDTO electionDTO) {
        // Convert DTO to entity before passing to service
        Election election = electionDTO.toEntity();
        return ResponseEntity.ok(ElectionDTO.fromEntity(electionService.createElection(election)));
    }

    // Update election
    @PutMapping("/{id}")
    public ResponseEntity<ElectionDTO> updateElection(@PathVariable Long id, @RequestBody ElectionDTO electionDTO) {
        try {
            // Convert DTO to entity before passing to service
            Election election = electionDTO.toEntity();
            return ResponseEntity.ok(ElectionDTO.fromEntity(electionService.updateElection(id, election)));
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
            return ResponseEntity.ok(ElectionDTO.fromEntity(updatedElection));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
