package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.adp.voting_system_server.model.Vote;
import project.adp.voting_system_server.repository.VoteRepository;

import jakarta.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    // In-memory cache for storing votes temporarily
    private final List<Vote> voteCache = new ArrayList<>();
    private final int BATCH_SIZE = 10; // Batch size threshold for saving votes

    // Save a vote to the cache
    public synchronized String saveVote(Vote vote) {
        // Check if a vote already exists for the same voterId and electionId
        if (voteExists(vote.getVoterId(), vote.getElectionId())) {
            return "Vote already exists for this user in this election.";
        }

        voteCache.add(vote); // Add vote to cache

        // If the cache size reaches the batch size, persist the votes to the database
        if (voteCache.size() >= BATCH_SIZE) {
            flushVotesToDatabase();
        }

        return "Vote cached successfully. It will be saved to the database in a batch.";
    }

    public boolean voteExists(String voterId, Long electionId) {
        if (voterId == null || electionId == null) {
            return false;
        }

        // Check if the vote exists in the cache
        boolean existsInCache = voteCache.stream()
                .anyMatch(vote -> voterId.equals(vote.getVoterId()) && electionId.equals(vote.getElectionId()));
        if (existsInCache) {
            return true;
        }

        // Check if the vote exists in the database
        Optional<Vote> existingVote = voteRepository.findByVoterIdAndElectionId(voterId, electionId);
        return existingVote.isPresent();
    }

    // Persist cached votes to the database
    @Transactional
    @CacheEvict(value = "votes", allEntries = true) // Invalidate cache after flushing
    public synchronized void flushVotesToDatabase() {
        if (!voteCache.isEmpty()) {
            voteRepository.saveAll(voteCache);
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
    @CacheEvict(value = "votes", allEntries = true) // Invalidate the cache before flushing
    public void autoFlushVotes() {
        System.out.println("Scheduled flush triggered.");
        flushVotesToDatabase();
    }

    // Fetch all votes from the database
    @Cacheable(value = "votes", key = "'allVotes'") // Cache the result with a static key
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    // Fetch votes by election ID and party ID
    @Cacheable(value = "votes", key = "#electionId + '_' + #partyId") // Cache by election and party
    public List<Vote> getVotesByElectionAndParty(Long electionId, String partyId) {
        return voteRepository.findAllByElectionIdAndPartyId(electionId, partyId);
    }

    // Count votes for a specific party in an election
    @Cacheable(value = "voteCounts", key = "#electionId + '_' + #partyId") // Cache vote count
    public Long countVotesForElectionAndParty(Long electionId, String partyId) {
        return voteRepository.countVotesByElectionIdAndPartyId(electionId, partyId);
    }

    // Fetch votes by voter ID (if needed, you can add this method)
    @Cacheable(value = "votesByVoter", key = "#voterId") // Cache votes by voter
    public List<Vote> getVotesByVoter(String voterId) {
        return voteRepository.findAllByVoterId(voterId);
    }

    // Get votes by voterId from both cache and database
    public List<Vote> getVotesByVoterId(String voterId) {
        // Get votes from cache
        List<Vote> votesFromCache = voteCache.stream()
                .filter(vote -> voterId.equals(vote.getVoterId()))
                .collect(Collectors.toList());

        // Get votes from database
        List<Vote> votesFromDatabase = voteRepository.findByVoterId(voterId);

        // Combine both lists
        List<Vote> allVotes = new ArrayList<>(votesFromDatabase);
        allVotes.addAll(votesFromCache);

        return allVotes;
    }

    @Transactional
    public void deleteVotesByElectionId(Long id) {
        // Delete votes from cache
        voteCache.removeIf(vote -> id.equals(vote.getElectionId()));

        // Delete votes from database
        voteRepository.deleteByElectionId(id);
    }
}
