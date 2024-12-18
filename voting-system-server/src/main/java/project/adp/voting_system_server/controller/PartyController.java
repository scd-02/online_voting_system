package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.service.PartyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    // Get all parties
    @GetMapping("/getAllParties")
    public ResponseEntity<List<Party>> getAllParties() {
        return ResponseEntity.ok(partyService.getAllParties());
    }

    // Get a party by ID
    @GetMapping("/{id}")
    public ResponseEntity<Party> getPartyById(@PathVariable Long id) {
        Optional<Party> party = partyService.getPartyById(id);
        return party.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new party
    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody Party party) {
        Party savedParty = partyService.saveParty(party);
        return ResponseEntity.ok(savedParty);
    }

    // Update a party
    @PutMapping("/{id}")
    public ResponseEntity<Party> updateParty(@PathVariable Long id, @RequestBody Party party) {
        if (!partyService.getPartyById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        party.setId(id); // Ensure the ID is not changed
        Party updatedParty = partyService.saveParty(party);
        return ResponseEntity.ok(updatedParty);
    }

    // Delete a party
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParty(@PathVariable Long id) {
        if (!partyService.getPartyById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        partyService.deleteParty(id);
        return ResponseEntity.noContent().build();
    }
}
