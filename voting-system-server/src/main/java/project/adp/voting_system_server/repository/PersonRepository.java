package project.adp.voting_system_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.adp.voting_system_server.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Person findByAadhaarNumber(@Param("aadhaarNumber") String aadhaarNumber);
}
