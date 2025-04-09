package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
//import com.example.demo.service.MoviesSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
public class GetByIdController {

    private MoviesSeriesRepository moviesSeriesRepository;
    private SeasonsRepository seasonsRepository;


    @Autowired
    public GetByIdController(MoviesSeriesRepository moviesSeriesRepository, SeasonsRepository seasonsRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
        this.seasonsRepository = seasonsRepository;
    }


    //получение меты по id фильма/сериала FILM
    @GetMapping("/{id}/movieseries")
    public ResponseEntity<MoviesSeries> getMoviesSeriesMetaBySeriesId(@PathVariable Long id) {
        MoviesSeries series = moviesSeriesRepository.findById(id).orElse(null);
        if (series != null) {
            return ResponseEntity.ok(series); // возвращаем с кодом 200 OK
        } else {
            return ResponseEntity.notFound().build(); // если объект не найден, возвращаем 404
        }
    }

    //получение всех сезонов и эпизодов по id сериала FILM
    @GetMapping("/{id}/seasonsnepisodes")
    public ResponseEntity<List<Seasons>> getSeasonsBySeriesId(@PathVariable Long id) {
        // Проверяем, существует ли сериал
        MoviesSeries moviesSeries = moviesSeriesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Series not found with ID: " + id));

        // Получаем сезоны по ID сериала
        List<Seasons> seasons = seasonsRepository.findByMoviesSeriesId(moviesSeries.getId());

        return ResponseEntity.ok(seasons);
    }


}

