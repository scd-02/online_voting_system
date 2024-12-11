package project.adp.voting_system_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.adp.voting_system_server.model.Admin;
import project.adp.voting_system_server.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(String userId) {
        return adminRepository.findById(userId);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(String userId, Admin adminDetails) {
        Optional<Admin> adminOptional = adminRepository.findById(userId);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            admin.setUserId(adminDetails.getUserId());
            return adminRepository.save(admin);
        }
        return null; // Or throw a custom exception if preferred.
    }

    public void deleteAdmin(String userId) {
        adminRepository.deleteById(userId);
    }
}
