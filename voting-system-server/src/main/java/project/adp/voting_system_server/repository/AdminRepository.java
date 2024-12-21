package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.adp.voting_system_server.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUserId(String userId); // Corrected method name
}
