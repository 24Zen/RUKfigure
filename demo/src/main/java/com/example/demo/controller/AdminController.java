package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;

@RestController
@RequestMapping("/api/admins") // CORS คุมที่ WebConfig แล้ว ไม่ต้องใส่ @CrossOrigin ตรงนี้
public class AdminController {

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // helper: ชื่อผู้กระทำ (จะได้ค่า username หลังต่อ JWT)
    private String actor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.getName() != null) ? auth.getName() : "anonymous";
    }

    // GET /api/admins
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // GET /api/admins/{id} → 200 หรือ 404 (ไม่โยน 500)
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/admins
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        Admin saved = adminService.createAdmin(admin);
        LOG.info("AUDIT {{\"entity\":\"admin\",\"action\":\"CREATE\",\"id\":{},\"by\":\"{}\"}}",
                saved.getId(), actor());
        return saved;
    }

    // PUT /api/admins/{id}
    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
        Admin updated = adminService.updateAdmin(id, admin);
        LOG.info("AUDIT {{\"entity\":\"admin\",\"action\":\"UPDATE\",\"id\":{},\"by\":\"{}\"}}",
                updated.getId(), actor());
        return updated;
    }

    // DELETE /api/admins/{id} → 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        LOG.info("AUDIT {{\"entity\":\"admin\",\"action\":\"DELETE\",\"id\":{},\"by\":\"{}\"}}",
                id, actor());
        return ResponseEntity.noContent().build();
    }
}
