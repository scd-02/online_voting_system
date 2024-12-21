package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.model.Person;
import project.adp.voting_system_server.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Create or Update a Person
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    // Fetch a Person by Aadhaar Number
    public Optional<Person> getPersonByAadhaar(String aadhaarNumber) {
        return personRepository.findById(aadhaarNumber);
    }

    // Fetch All Persons
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    // Delete a Person by Aadhaar Number
    public void deletePersonByAadhaar(String aadhaarNumber) {
        personRepository.deleteById(aadhaarNumber);
    }
}
