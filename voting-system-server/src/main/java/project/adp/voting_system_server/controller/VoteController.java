package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.model.Vote;
import project.adp.voting_system_server.service.VoteService;

import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    // Save a new vote
    @PostMapping
    public ResponseEntity<Vote> saveVote(@RequestBody Vote vote) {
        return ResponseEntity.ok(voteService.saveVote(vote));
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
