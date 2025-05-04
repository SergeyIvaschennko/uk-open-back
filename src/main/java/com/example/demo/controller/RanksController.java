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
public class RanksController {

    private MoviesSeriesRepository moviesSeriesRepository;


    @Autowired
    public RanksController(MoviesSeriesRepository moviesSeriesRepository, TypeOfContentRepository typeOfContentRepository, SeasonsRepository seasonsRepository, EpisodesRepository episodesRepository, WordsOfContentRepository wordsOfContentRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
    }


    //самые лучшие сериалы
    @GetMapping("/top-series")
    public List<MoviesSeries> listSeries() {
        return moviesSeriesRepository.findRandomTopRatedByType(1L);
    }

    //самые лучшие сериалы
    @GetMapping("/top-movies")
    public List<MoviesSeries> listMovies() {
        return moviesSeriesRepository.findRandomTopRatedByType(2L);
    }

    //самые лучшие сериалы
    @GetMapping("/top-cartoons")
    public List<MoviesSeries> listCartoons() {
        return moviesSeriesRepository.findRandomTopRatedByType(3L);
    }

    //новинки
    @GetMapping("/released-in-2024-or-2025")
    public ResponseEntity<List<MoviesSeries>> getMoviesReleasedIn2024Or2025() {
        List<MoviesSeries> movies = moviesSeriesRepository.findMoviesReleasedIn2024Or2025();
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Возвращаем 204, если нет фильмов
        }
        return ResponseEntity.ok(movies); // Возвращаем 200 и список фильмов
    }

}

