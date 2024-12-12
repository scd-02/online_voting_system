package project.adp.voting_system_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.adp.voting_system_server.model.Admin;
import project.adp.voting_system_server.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Admin> getAdminById(@PathVariable String userId) {
        Admin admin = adminService.getAdminById(userId); // Correct method
        return admin != null ? ResponseEntity.ok(admin) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.createAdmin(admin);
        return ResponseEntity.ok(createdAdmin);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String userId, @RequestBody Admin adminDetails) {
        Admin updatedAdmin = adminService.updateAdmin(userId, adminDetails);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable String userId) {
        adminService.deleteAdmin(userId);
        return ResponseEntity.noContent().build();
    }
}
