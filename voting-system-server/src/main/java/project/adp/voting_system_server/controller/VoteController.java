package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.model.Vote;
import project.adp.voting_system_server.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    // Save a new vote (cached and batched)
    @PostMapping
    public ResponseEntity<String> saveVote(@RequestBody Vote vote) {
        voteService.saveVote(vote);
        return ResponseEntity.ok("Vote cached successfully. It will be saved to the database in a batch.");
    }

    // Endpoint to manually flush the cache
    @PostMapping("/flush")
    public ResponseEntity<String> flushVotes() {
        voteService.flushVotesToDatabase();
        return ResponseEntity.ok("Cached votes flushed to the database.");
    }

    // Get all votes
    @GetMapping
    public ResponseEntity<List<Vote>> getAllVotes() {
        return ResponseEntity.ok(voteService.getAllVotes());
    }

    // Get votes by election and party ID
    @GetMapping("/election/{electionId}/party/{partyId}")
    public ResponseEntity<List<Vote>> getVotesByElectionAndParty(
            @PathVariable Long electionId,
            @PathVariable String partyId) {
        return ResponseEntity.ok(voteService.getVotesByElectionAndParty(electionId, partyId));
    }

    // Count votes for a party in an election
    @GetMapping("/election/{electionId}/party/{partyId}/count")
    public ResponseEntity<Long> countVotesForElectionAndParty(
            @PathVariable Long electionId,
            @PathVariable String partyId) {
        return ResponseEntity.ok(voteService.countVotesForElectionAndParty(electionId, partyId));
    }
}
