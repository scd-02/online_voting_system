package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.adp.voting_system_server.model.Election;
import project.adp.voting_system_server.model.Party;
import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.repository.ElectionRepository;
import project.adp.voting_system_server.repository.PartyRepository;
import project.adp.voting_system_server.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Optional<Election> getElectionById(Long id) {
        return electionRepository.findById(id);
    }

    public List<Election> getElectionsByRegion(String region) {
        return electionRepository.findByState(region);
    }

    @Transactional
    public Election createElection(Election electionPayload) {
        // Step 1: Save the basic election details
        Election election = new Election();
        election.setName(electionPayload.getName());
        election.setState(electionPayload.getState());
        election.setEligiblePartys(electionPayload.getEligiblePartys());
        election.setActive(electionPayload.isActive());
        election = electionRepository.save(election);

        // Step 3: Fetch all users from the region and associate them with the election
        addElectionToEligibleUser(election);

        return election;
    }

    public Election updateElection(Long id, Election electionDetails) {
        return electionRepository.findById(id).map(election -> {
            election.setName(electionDetails.getName());
            election.setState(electionDetails.getState());
            election.setEligiblePartys(electionDetails.getEligiblePartys());
            return electionRepository.save(election);
        }).orElseThrow(() -> new RuntimeException("Election not found with id " + id));
    }

    public void deleteElection(Long id) {
        electionRepository.findById(id).ifPresent(this::removeElectionFromEligibleUser);
        electionRepository.deleteById(id);
    }

    public void addElectionToEligibleUser(Election election) {
        List<User> usersInRegion;
        if (election.getState().equalsIgnoreCase("India")) {
            usersInRegion = userRepository.findAll();
        } else {
            usersInRegion = userRepository.findByState(election.getState());
        }

        for (User user : usersInRegion) {
            List<String> userElectionList = user.getElectionList();
            if (userElectionList == null) {
                userElectionList = new ArrayList<>();
            }
            userElectionList.add(String.valueOf(election.getId()));
            user.setElectionList(userElectionList);
            userRepository.save(user);
        }
    }

    public Election setActiveStatus(Long id, boolean status) {
        return electionRepository.findById(id).map(election -> {
            election.setActive(status);
            removeElectionFromEligibleUser(election);
            return electionRepository.save(election);
        }).orElseThrow(() -> new RuntimeException("Election not found with id " + id));
    }

    public void removeElectionFromEligibleUser(Election election) {
        System.out.println("-----------------------------------");
        List<User> usersInRegion;
        if (election.getState().equalsIgnoreCase("India")) {
            usersInRegion = userRepository.findAll();
        } else {
            usersInRegion = userRepository.findByState(election.getState());
        }

        for (User user : usersInRegion) {
            List<String> userElectionList = user.getElectionList();
            if (userElectionList != null) {
                userElectionList = userElectionList.stream()
                        .filter(electionId -> !electionId.equals(String.valueOf(election.getId())))
                        .collect(Collectors.toList());
                user.setElectionList(userElectionList);
                userRepository.save(user);
            }
        }
    }
}
