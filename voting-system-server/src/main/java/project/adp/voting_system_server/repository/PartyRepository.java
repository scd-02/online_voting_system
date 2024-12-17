package project.adp.voting_system_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.adp.voting_system_server.model.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {
    List<Party> findByState(String state);
}
