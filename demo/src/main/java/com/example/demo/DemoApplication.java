package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;  // ✅ ต้อง import

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Seed admin ถ้า table ว่าง
    @Bean
    @SuppressWarnings("unused")
    CommandLineRunner initData(AdminRepository repo, PasswordEncoder encoder) {
        return args -> {
            long count = repo.count();
            if (count == 0) {
                Admin admin = new Admin("admin1", encoder.encode("1234"), "admin@example.com");
                repo.save(admin);
                System.out.println("[INIT] Seeded default admin: admin1");
            } else {
                System.out.println("[INIT] Admin table already has data: " + count + " record(s).");
            }
        };
    }

    // ✅ ใช้ครั้งเดียวเพื่อแปลง plain text -> BCrypt แล้วคอมเมนต์ออก
    @Bean
    CommandLineRunner bcryptOnce(AdminRepository repo, PasswordEncoder enc) {
        return args -> {
            repo.findAll().forEach(a -> {
                String p = a.getPassword();
                if (p == null || p.isBlank()) return;
                if (p.startsWith("$2a$") || p.startsWith("$2b$") || p.startsWith("$2y$")) return; // already bcrypt
                a.setPassword(enc.encode(p));
                repo.save(a);
                System.out.println("BCrypt encoded -> " + a.getUsername());
            });
        };
    }
}

