package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    public Admin createAdmin(Admin admin) {
        if (admin.getUsername() == null || admin.getUsername().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is required");
        }
        if (repo.existsByUsername(admin.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        return repo.save(admin);
    }

    @Transactional
    public Admin updateAdmin(Long id, Admin incoming) {
        Admin existing = repo.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found with id " + id));

        // เปลี่ยน username (ถ้ามี) และต้องไม่ซ้ำกับคนอื่น
        if (incoming.getUsername() != null && !incoming.getUsername().equals(existing.getUsername())) {
            if (repo.existsByUsername(incoming.getUsername())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
            }
            existing.setUsername(incoming.getUsername());
        }

        if (incoming.getEmail() != null) {
            existing.setEmail(incoming.getEmail());
        }

        // อัปเดตรหัสผ่านเฉพาะกรณีส่งมาและไม่ว่าง
        if (incoming.getPassword() != null && !incoming.getPassword().isBlank()) {
            existing.setPassword(incoming.getPassword());
        }

        return repo.save(existing);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found with id " + id);
        }
        repo.deleteById(id);
    }
}
