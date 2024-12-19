package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.service.ElectionService;

import java.util.List;

@RestController
@RequestMapping("/election")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @GetMapping("/getAllElections")
    public ResponseEntity<List<Election>> getAllElections() {
        return ResponseEntity.ok(electionService.getAllElections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Election> getElectionById(@PathVariable Long id) {
        return electionService.getElectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<Election>> getElectionsByRegion(@PathVariable String region) {
        return ResponseEntity.ok(electionService.getElectionsByRegion(region));
    }

    @PostMapping("/create")
    public ResponseEntity<Election> createElection(@RequestBody Election election) {
        return ResponseEntity.ok(electionService.createElection(election));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Election> updateElection(@PathVariable Long id, @RequestBody Election electionDetails) {
        try {
            return ResponseEntity.ok(electionService.updateElection(id, electionDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.noContent().build();
    }
}
