package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.model.Vote;
import project.adp.voting_system_server.repository.VoteRepository;

import java.util.List;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    // Save a new vote
    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    // Fetch all votes
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // Fetch votes by election and party ID
    public List<Vote> getVotesByElectionAndParty(Long electionId, String partyId) {
        return voteRepository.findAllByElectionIdAndPartyId(electionId, partyId);
    }

    // Count total votes for a specific party in an election
    public Long countVotesForElectionAndParty(Long electionId, String partyId) {
        return voteRepository.countVotesByElectionIdAndPartyId(electionId, partyId);
    }
}
