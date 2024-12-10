package project.adp.voting_system_server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Get list of users filtered by state.
     *
     * @param state The state to filter users by.
     * @return List of users from the specified state.
     */
    public List<User> getUsersByState(String state) {
        return userRepository.findByState(state);
    }

    /**
     * Add an election to the user's election list.
     *
     * @param aadhaarNumber The Aadhaar number of the user.
     * @param electionName  The name of the election to be added.
     * @return Updated User object.
     * @throws IllegalArgumentException if the user is not found.
     */
    public User addToElectionList(String aadhaarNumber, String electionName) {
        Optional<User> userOptional = userRepository.findById(aadhaarNumber);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Initialize electionList if null
            if (user.getElectionList() == null) {
                user.setElectionList(new ArrayList<>());
            }

            // Add election name to the election list if not already present
            if (!user.getElectionList().contains(electionName)) {
                user.getElectionList().add(electionName);
                userRepository.save(user);
            }
            return user;
        } else {
            throw new IllegalArgumentException("User not found with Aadhaar number: " + aadhaarNumber);
        }
    }
}
