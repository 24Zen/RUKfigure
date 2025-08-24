package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.model.Figure;
import com.example.demo.repository.FigureRepository;
import java.util.List;
import java.util.Optional;

@Service
public class FigureService {
    private final FigureRepository figureRepository;

    public FigureService(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    public List<Figure> getAllFigures() { return figureRepository.findAll(); }
    public Optional<Figure> getFigureByCode(String code) { return figureRepository.findById(code); }
    public Figure saveFigure(Figure figure) { return figureRepository.save(figure); }
    public void deleteFigure(String code) { figureRepository.deleteById(code); }
}
