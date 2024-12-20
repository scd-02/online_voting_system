package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import project.adp.voting_system_server.dto.PartyDTO;
import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.service.CandidateService;
import project.adp.voting_system_server.service.PartyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/party")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private CandidateService candidateService;

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
    public ResponseEntity<?> createParty(@RequestBody PartyDTO partyDTO) {
        try {
            // Check if party already exists
            Optional<Party> existingParty = partyService.getPartyByName(partyDTO.getName());
            if (existingParty.isPresent()) {
                return ResponseEntity.badRequest().body("Party with the given name already exists.");
            }

            // Create new party with leader as null
            Party newParty = new Party();
            newParty.setName(partyDTO.getName());
            newParty.setAgenda(partyDTO.getAgenda());
            newParty.setState(partyDTO.getState());
            newParty.setLeader(null);
            partyService.saveParty(newParty);

            // If leader Aadhaar number is provided
            if (partyDTO.getLeaderAadhaarNumber() != null && !partyDTO.getLeaderAadhaarNumber().isEmpty()) {
                // Check if candidate already exists
                Optional<Candidate> existingCandidate = candidateService
                        .getCandidateByAadhaarNumber(partyDTO.getLeaderAadhaarNumber());
                if (existingCandidate.isPresent()) {
                    return ResponseEntity.badRequest().body("Candidate with the given Aadhaar number already exists.");
                }

                // Create new candidate and associate with the party
                Candidate newLeader = new Candidate();
                newLeader.setAadhaarNumber(partyDTO.getLeaderAadhaarNumber());
                newLeader.setParty(newParty);
                candidateService.saveCandidate(newLeader);

                // Set the candidate as the leader of the party
                newParty.setLeader(newLeader);
                partyService.saveParty(newParty);
            }

            return ResponseEntity.ok(new PartyDTO(newParty));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating party: " + e.getMessage());
        }
    }

    // Update an existing party
    @PutMapping("/{name}")
    public ResponseEntity<?> updateParty(@PathVariable String name, @RequestBody PartyDTO partyDTO) {
        try {
            Optional<Party> existingPartyOpt = partyService.getPartyByName(name);
            if (!existingPartyOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Party existingParty = existingPartyOpt.get();

            if (partyDTO.getState() != null && !partyDTO.getState().equals(existingParty.getState())) {
                boolean partyInOngoingElection = partyService.partyInOngoingElection(name);
                if (partyInOngoingElection) {
                    return ResponseEntity.badRequest()
                            .body("Cannot update party state while it is eligible in an ongoing election.");
                }
            }

            existingParty.setAgenda(partyDTO.getAgenda());
            existingParty.setState(partyDTO.getState());

            // If leader Aadhaar number is provided
            if (partyDTO.getLeaderAadhaarNumber() != null && !partyDTO.getLeaderAadhaarNumber().isEmpty()) {
                Optional<Candidate> leaderOpt = candidateService
                        .getCandidateByAadhaarNumber(partyDTO.getLeaderAadhaarNumber());
                if (leaderOpt.isPresent()) {
                    Candidate leader = leaderOpt.get();
                    existingParty.setLeader(leader);
                } else {
                    return ResponseEntity.badRequest().body("Candidate with the given Aadhaar number does not exist.");
                }
            }

            partyService.saveParty(existingParty);
            return ResponseEntity.ok(new PartyDTO(existingParty));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error updating party: " + e.getMessage());
        }
    }

    // // Set the leader of a party
    // @PutMapping("/{name}/setLeader")
    // public ResponseEntity<Party> setLeader(@PathVariable String name,
    // @RequestParam String leaderAadhaarNumber) {
    // try {
    // Party updatedParty = partyService.setPartyLeader(name, leaderAadhaarNumber);
    // return ResponseEntity.ok(updatedParty);
    // } catch (RuntimeException e) {
    // return ResponseEntity.badRequest().body(null);
    // }
    // }

    /**
     * Deletes a party by name.
     *
     * @param name The name of the party to delete.
     * @return ResponseEntity indicating the result of the deletion.
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteParty(@PathVariable String name) {
        try {
            partyService.deleteParty(name);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Log the exception details for debugging purposes
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the party.");
        }
    }
}
