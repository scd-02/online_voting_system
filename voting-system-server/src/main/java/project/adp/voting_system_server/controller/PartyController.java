package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.dto.PartyDTO;
import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.service.PartyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    // Get all parties
    @GetMapping("/getAllParties")
    public List<PartyDTO> getAllParties() {
        List<Party> parties = partyService.getAllParties();
        return parties.stream()
                .map(PartyDTO::new)
                .collect(Collectors.toList());
    }

    // Get a party by name
    @GetMapping("/{name}")
    public ResponseEntity<Party> getPartyByName(@PathVariable String name) {
        Optional<Party> party = partyService.getPartyByName(name);
        return party.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new party
    @PostMapping
    public ResponseEntity<Party> createParty(@RequestBody Party party) {
        Party savedParty = partyService.saveParty(party);
        return ResponseEntity.ok(savedParty);
    }

    // Update an existing party
    @PutMapping("/{name}")
    public ResponseEntity<Party> updateParty(@PathVariable String name, @RequestBody Party party) {
        Optional<Party> existingPartyOpt = partyService.getPartyByName(name);
        if (existingPartyOpt.isPresent()) {
            Party existingParty = existingPartyOpt.get();
            existingParty.setAgenda(party.getAgenda());
            existingParty.setState(party.getState());
            existingParty.setLeader(party.getLeader());
            Party updatedParty = partyService.saveParty(existingParty);
            return ResponseEntity.ok(updatedParty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Set the leader of a party
    @PutMapping("/{name}/setLeader")
    public ResponseEntity<Party> setLeader(@PathVariable String name, @RequestParam String leaderAadhaarNumber) {
        try {
            Party updatedParty = partyService.setPartyLeader(name, leaderAadhaarNumber);
            return ResponseEntity.ok(updatedParty);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete a party
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteParty(@PathVariable String name) {
        if (!partyService.getPartyByName(name).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        partyService.deleteParty(name);
        return ResponseEntity.noContent().build();
    }
}
