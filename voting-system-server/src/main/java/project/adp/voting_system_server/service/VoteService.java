package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import project.adp.voting_system_server.model.Vote;
import project.adp.voting_system_server.repository.VoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    // In-memory cache for storing votes temporarily
    private final List<Vote> voteCache = new ArrayList<>();
    private final int BATCH_SIZE = 10; // Batch size threshold for saving votes

    // Save a vote to the cache
    public synchronized void saveVote(Vote vote) {
        voteCache.add(vote); // Add vote to cache

        // If the cache size reaches the batch size, persist the votes to the database
        if (voteCache.size() >= BATCH_SIZE) {
            flushVotesToDatabase();
        }
    }

    // Persist cached votes to the database
    @Transactional
    public synchronized void flushVotesToDatabase() {
        if (!voteCache.isEmpty()) {
            // Group votes by electionId for consistent batching
            Map<Long, List<Vote>> groupedVotes = voteCache.stream()
                    .collect(Collectors.groupingBy(Vote::getElectionId));

            // Persist each group to the database
            groupedVotes.values().forEach(voteRepository::saveAll);

            // Clear the cache after saving
            voteCache.clear();
        }
    }

    // Ensure all cached votes are saved before application shutdown
    @PreDestroy
    public void onShutdown() {
        flushVotesToDatabase();
    }

    // Scheduled method to flush votes every 15 minutes
    @Scheduled(fixedRate = 900000) // 15 minutes in milliseconds
    public void autoFlushVotes() {
        System.out.println("Scheduled flush triggered.");
        flushVotesToDatabase();
    }

    // Fetch all votes from the database
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // Fetch votes by election ID and party ID
    public List<Vote> getVotesByElectionAndParty(Long electionId, String partyId) {
        return voteRepository.findAllByElectionIdAndPartyId(electionId, partyId);
    }

    // Count votes for a specific party in an election
    public Long countVotesForElectionAndParty(Long electionId, String partyId) {
        return voteRepository.countVotesByElectionIdAndPartyId(electionId, partyId);
    }
}
