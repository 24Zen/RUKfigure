package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Comic;
import com.example.demo.repository.ComicRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ComicService {
    private final ComicRepository comicRepository;

    public ComicService(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    public List<Comic> getAllComics() { return comicRepository.findAll(); }
    public Optional<Comic> getComicByCode(String code) { return comicRepository.findById(code); }
    public Comic saveComic(Comic comic) { return comicRepository.save(comic); }
    public void deleteComic(String code) { comicRepository.deleteById(code); }
}
