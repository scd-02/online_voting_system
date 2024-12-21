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
        String result = voteService.saveVote(vote);
        if (result.equals("Vote already exists for this user in this election.")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
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

    // Get votes by voterId
    @GetMapping("/voter/{voterId}")
    public ResponseEntity<List<Vote>> getVotesByVoterId(@PathVariable String voterId) {
        List<Vote> votes = voteService.getVotesByVoterId(voterId);
        return ResponseEntity.ok(votes);
    }
}
