package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminService {

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    public List<Admin> getAllAdmins() {
        return repo.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return repo.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        return repo.save(admin);
    }

    public Admin updateAdmin(Long id, Admin admin) {
        return repo.findById(id)
                .map(a -> {
                    a.setUsername(admin.getUsername());
                    a.setPassword(admin.getPassword());
                    a.setEmail(admin.getEmail());
                    return repo.save(a);
                })
                .orElseThrow(() -> new RuntimeException("Admin not found with id " + id));
    }

    public void deleteAdmin(Long id) {
        repo.deleteById(id);
    }
}
