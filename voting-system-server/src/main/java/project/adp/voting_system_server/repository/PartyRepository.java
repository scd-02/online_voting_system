package project.adp.voting_system_server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.adp.voting_system_server.model.Candidate;
import project.adp.voting_system_server.model.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, String> {
    List<Party> findByState(String state);
    Optional<Party> findByLeader(Candidate candidate);
}
