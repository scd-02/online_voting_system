package project.adp.voting_system_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.adp.voting_system_server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Custom query to find users by the first digit of the PIN code
    User findByAadhaarNumber(@Param("aadhaarNumber") String aadhaarNumber);

    List<User> findByState(String state);
}
