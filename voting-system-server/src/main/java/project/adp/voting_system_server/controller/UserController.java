package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to save or update a user
    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userService.saveOrUpdateUser(user);
    }

    // Endpoint to get user by Aadhaar number
    @GetMapping("/{aadhaarNumber}")
    public User getUser(@PathVariable String aadhaarNumber) {
        return userService.getUserByAadhaarNumber(aadhaarNumber);
    }
}
