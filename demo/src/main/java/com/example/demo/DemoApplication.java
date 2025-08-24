package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) { SpringApplication.run(DemoApplication.class, args); }

    @Bean
    CommandLineRunner run(AdminRepository repo) {
        return args -> {
            Admin admin = new Admin("admin1", "1234", "admin@example.com");
            repo.save(admin);
            System.out.println("Admin saved: " + repo.findAll());
        };
    }
}
