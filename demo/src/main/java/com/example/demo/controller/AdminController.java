package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminRepository repo;
    public AdminController(AdminRepository repo) { this.repo = repo; }

    @GetMapping public List<Admin> getAll() { return repo.findAll(); }
    @GetMapping("/{id}") public ResponseEntity<Admin> getById(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public Admin create(@RequestBody Admin admin) { return repo.save(admin); }
    @PutMapping("/{id}") public ResponseEntity<Admin> update(@PathVariable Long id, @RequestBody Admin a) {
        return repo.findById(id).map(admin -> {
            admin.setUsername(a.getUsername());
            admin.setPassword(a.getPassword());
            admin.setEmail(a.getEmail());
            repo.save(admin);
            return ResponseEntity.ok(admin);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) {
        return repo.findById(id).map(admin -> {
            repo.delete(admin); return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
