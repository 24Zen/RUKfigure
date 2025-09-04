package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthenticationManager authManager; private final JwtUtil jwt;
  public AuthController(AuthenticationManager am, JwtUtil j){ this.authManager=am; this.jwt=j; }

  public record LoginRequest(String username, String password){}
  public record LoginResponse(String token, String username){}

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
    Authentication a = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.username(), req.password()));
    String token = jwt.generate(req.username());
    return ResponseEntity.ok(new LoginResponse(token, req.username()));
  }
}
