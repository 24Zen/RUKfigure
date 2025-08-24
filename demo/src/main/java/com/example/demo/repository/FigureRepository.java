package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Figure;

public interface FigureRepository extends JpaRepository<Figure, String> {
}
