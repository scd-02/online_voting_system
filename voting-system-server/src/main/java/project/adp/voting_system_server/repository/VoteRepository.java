package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.adp.voting_system_server.model.Vote;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Find votes by election and party
    List<Vote> findAllByElectionIdAndPartyId(Long electionId, String partyId);

    // Count votes for a specific party in an election
    @Query("SELECT COUNT(v) FROM Vote v WHERE v.electionId = :electionId AND v.partyId = :partyId")
    Long countVotesByElectionIdAndPartyId(Long electionId, String partyId);

    // Add a method to find a vote by voterId and electionId
    Optional<Vote> findByVoterIdAndElectionId(String voterId, Long electionId);

    // Add a method to find votes by voterId
    List<Vote> findByVoterId(String voterId);

    void deleteByElectionId(Long id);

    List<Vote> findAllByVoterId(String voterId);
}
