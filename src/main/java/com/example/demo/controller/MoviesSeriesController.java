package com.example.demo.controller;

import com.example.demo.model.MoviesSeries;
import com.example.demo.model.TypeOfContent;
import com.example.demo.repository.MoviesSeriesRepository;
import com.example.demo.repository.TypeOfContentRepository;
//import com.example.demo.service.MoviesSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MoviesSeriesController {

    private MoviesSeriesRepository moviesSeriesRepository;
    private TypeOfContentRepository typeOfContentRepository;

    @Autowired
    public MoviesSeriesController(TypeOfContentRepository typeOfContentRepository) {
        this.typeOfContentRepository = typeOfContentRepository;
    }


    // Получить список сериалов с лучшим рейтингом по убыванию
    @GetMapping("/audio/files")
//    @ResponseBody
    public List<TypeOfContent> listAudioFiles() {
        return typeOfContentRepository.findAll();
    }






}
