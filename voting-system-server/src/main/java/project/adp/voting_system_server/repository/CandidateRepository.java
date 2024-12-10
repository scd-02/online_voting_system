package project.adp.voting_system_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.adp.voting_system_server.model.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    // Custom query methods, if needed, e.g., find by party_id or aadhaarNumber
    List<Candidate> findByPartyId(Long partyId);

    List<Candidate> findByAadhaarNumber(String aadhaarNumber);
}
