package project.adp.voting_system_server.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.model.Person;
import project.adp.voting_system_server.repository.ElectionRepository;
import project.adp.voting_system_server.repository.PersonRepository;
import project.adp.voting_system_server.repository.UserRepository;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ElectionRepository electionRepository;

    @PostMapping("{aadhaarNumber}")
    public String postMethodName(@PathVariable String aadhaarNumber) {
        try {
            Person person = personRepository.findByAadhaarNumber(aadhaarNumber);
            User user = new User(person);

            // fetch all the election that the user is eligible
            final List<String> userElectionList = (user.getElectionList() == null) ? new ArrayList<>()
                    : user.getElectionList();
            electionRepository.findByState(person.getState()).forEach(
                    election -> {
                        userElectionList.add(String.valueOf(election.getId()));
                    });
            electionRepository.findByState("India").forEach(
                    election -> {
                        userElectionList.add(String.valueOf(election.getId()));
                    });
            user.setElectionList(userElectionList);
            userRepository.save(user);
            return "Registered User:\n" + user.toString();

        } catch (Exception e) {
            return "Error" + e.toString();
        }
    }

}
