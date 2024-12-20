package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import project.adp.voting_system_server.model.Election;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
    // Custom query to find elections by region
    List<Election> findByState(String state);

    // Remove all instance of a party when deleted
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM election_eligible_partys WHERE party_name = :partyName", nativeQuery = true)
    void deleteByPartyName(String partyName);

    // Returns true if any election has this party_name in its eligiblePartys list
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END FROM Election e JOIN e.eligiblePartys p WHERE p = :partyName")
    boolean existsPartyInElections(String partyName);
}
