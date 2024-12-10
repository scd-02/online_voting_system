package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.adp.voting_system_server.model.Election;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
    // Custom query to find elections by region
    List<Election> findByRegion(String region);
}
