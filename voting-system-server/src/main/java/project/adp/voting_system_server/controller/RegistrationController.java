package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.model.Person;

import project.adp.voting_system_server.repository.PersonRepository;
import project.adp.voting_system_server.repository.UserRepository;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("{aadhaarNumber}")
    public String postMethodName(@PathVariable String aadhaarNumber) {
        try {
            Person person = personRepository.findByAadhaarNumber(aadhaarNumber);
            User user = new User(person);
            userRepository.save(user);

            return "Registered User:\n" + user.toString();

        } catch (Exception e) {
            return "Error" + e.toString();
        }
    }

}
