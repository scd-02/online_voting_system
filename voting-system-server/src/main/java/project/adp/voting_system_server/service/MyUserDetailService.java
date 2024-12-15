package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.adp.voting_system_server.model.User;
import project.adp.voting_system_server.model.UserPrincipal;
import project.adp.voting_system_server.repository.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // here for authentication we are using aadhaar number as username
        User user = userRepository.findByAadhaarNumber(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }

}
