package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.model.Person;
import project.adp.voting_system_server.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Create or Update a Person
    @PostMapping
    public Person createOrUpdatePerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    // Get a Person by Aadhaar Number
    @GetMapping("/{aadhaarNumber}")
    public Person getPersonByAadhaar(@PathVariable String aadhaarNumber) {
        return personService.getPersonByAadhaar(aadhaarNumber).orElse(null); // Return `null` if not found
    }

    // Get All Persons
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons(); // Return empty list if no persons are found
    }

    // Delete a Person by Aadhaar Number
    @DeleteMapping("/{aadhaarNumber}")
    public void deletePersonByAadhaar(@PathVariable String aadhaarNumber) {
        personService.deletePersonByAadhaar(aadhaarNumber); // Return nothing (void)
    }
}
