package com.example.demo.security.userdetails;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminUserDetailsService implements UserDetailsService {
  private final AdminRepository repo;
  public AdminUserDetailsService(AdminRepository repo){ this.repo = repo; }

  @Override
  public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    Admin a = repo.findByUsername(login)
        .or(() -> repo.findByEmail(login))
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new User(a.getUsername(), a.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
  }
}
