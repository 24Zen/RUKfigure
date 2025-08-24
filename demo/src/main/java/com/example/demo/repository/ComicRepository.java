package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Comic;

public interface ComicRepository extends JpaRepository<Comic, String> {
}
