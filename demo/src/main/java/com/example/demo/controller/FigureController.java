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

import com.example.demo.model.Figure;
import com.example.demo.repository.FigureRepository;

@RestController
@RequestMapping("/api/figures")
public class FigureController {
    private final FigureRepository repo;
    public FigureController(FigureRepository repo) { this.repo = repo; }

    @GetMapping public List<Figure> getAll() { return repo.findAll(); }
    @GetMapping("/{code}") public ResponseEntity<Figure> getById(@PathVariable String code) {
        return repo.findById(code).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping public Figure create(@RequestBody Figure f) { return repo.save(f); }
    @PutMapping("/{code}") public ResponseEntity<Figure> update(@PathVariable String code, @RequestBody Figure f) {
        return repo.findById(code).map(fig -> {
            fig.setAnimeName(f.getAnimeName());
            fig.setFigureName(f.getFigureName());
            fig.setImageUrl(f.getImageUrl());
            repo.save(fig);
            return ResponseEntity.ok(fig);
        }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{code}") public ResponseEntity<Void> delete(@PathVariable String code) {
        return repo.findById(code).map(fig -> {
            repo.delete(fig); return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
