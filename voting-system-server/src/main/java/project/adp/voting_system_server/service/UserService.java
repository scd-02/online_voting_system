package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save or update user
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }

    // Get user by Aadhaar number
    public User getUserByAadhaarNumber(String aadhaarNumber) {
        return userRepository.findById(aadhaarNumber).orElse(null);
    }

    // Other service methods (delete, find all users, etc.)
}
