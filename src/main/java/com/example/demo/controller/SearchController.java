package com.example.demo.controller;


import com.example.demo.model.MoviesSeries;
import com.example.demo.repository.MoviesSeriesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/search")
public class SearchController {

    private MoviesSeriesRepository moviesSeriesRepository;

    public SearchController(MoviesSeriesRepository moviesSeriesRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
    }

    @GetMapping
    public List<MoviesSeries> searchMoviesSeries(@RequestParam("q") String query) {
        return moviesSeriesRepository.searchBySimilarName(query);
    }
}


