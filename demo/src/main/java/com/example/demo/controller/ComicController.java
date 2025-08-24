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

import com.example.demo.model.Comic;
import com.example.demo.repository.ComicRepository;

@RestController
@RequestMapping("/api/comics")
public class ComicController {
    private final ComicRepository repo;
    public ComicController(ComicRepository repo) { this.repo = repo; }

    @GetMapping public List<Comic> getAll() { return repo.findAll(); }
    @GetMapping("/{code}") public ResponseEntity<Comic> getById(@PathVariable String code) {
        return repo.findById(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public Comic create(@RequestBody Comic c) { return repo.save(c); }
    @PutMapping("/{code}") public ResponseEntity<Comic> update(@PathVariable String code, @RequestBody Comic c) {
        return repo.findById(code).map(comic -> {
            comic.setAnimeName(c.getAnimeName());
            comic.setComicName(c.getComicName());
            comic.setImageUrl(c.getImageUrl());
            repo.save(comic);
            return ResponseEntity.ok(comic);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{code}") public ResponseEntity<Void> delete(@PathVariable String code) {
        return repo.findById(code).map(comic -> {
            repo.delete(comic); return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
