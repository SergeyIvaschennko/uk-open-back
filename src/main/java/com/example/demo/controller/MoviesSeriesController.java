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

@RestController
public class MoviesSeriesController {

    private MoviesSeriesRepository moviesSeriesRepository;
    private TypeOfContentRepository typeOfContentRepository;
    private SeasonsRepository seasonsRepository;
    private EpisodesRepository episodesRepository;
    private WordsOfContentRepository wordsOfContentRepository;

    @Autowired
    public MoviesSeriesController(MoviesSeriesRepository moviesSeriesRepository, TypeOfContentRepository typeOfContentRepository, SeasonsRepository seasonsRepository, EpisodesRepository episodesRepository, WordsOfContentRepository wordsOfContentRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
        this.typeOfContentRepository = typeOfContentRepository;
        this.seasonsRepository = seasonsRepository;
        this.episodesRepository = episodesRepository;
        this.wordsOfContentRepository = wordsOfContentRepository;
    }


    // Получить список сериалов с лучшим рейтингом по убыванию
    @GetMapping("/types")
    public List<TypeOfContent> listAudioFiles() {
        return typeOfContentRepository.findAll();
    }

    //самые лучшие сериалы
    @GetMapping("/top-series")
    public List<MoviesSeries> listMovies() {
        return moviesSeriesRepository.findTopRatedSeries();
    }

    //получение меты по id фильма
    @GetMapping("/{seriesId}/meta")
    public ResponseEntity<MoviesSeriesMeta> getMoviesSeriesMetaBySeriesId(@PathVariable Long seriesId) {
        MoviesSeriesMeta meta = moviesSeriesRepository.findMetaBySeriesId(seriesId);
        if (meta != null) {
            return ResponseEntity.ok(meta); // возвращаем с кодом 200 OK
        } else {
            return ResponseEntity.notFound().build(); // если объект не найден, возвращаем 404
        }
    }

    //получение всех сезонов и эпизодов
    @GetMapping("/{name}/seasonsnepisodes")
    public ResponseEntity<List<Seasons>> getSeasonsBySeriesName(@PathVariable String name) {
        // Найти сериал по имени
        MoviesSeries moviesSeries = moviesSeriesRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Series not found with name: " + name));

        // Получить все сезоны для найденного сериала
        List<Seasons> seasons = seasonsRepository.findByMoviesSeriesId(moviesSeries.getId());

        return ResponseEntity.ok(seasons);
    }

    //слова для определенного эпизода сериала
    @GetMapping("/find/series")
    public ResponseEntity<List<Word>> getWordsBySeriesNameAndEpisodeId(
            @RequestParam String seriesName,
            @RequestParam Long episodeId) {

        // Найти сериал по его имени
        MoviesSeries moviesSeries = moviesSeriesRepository.findByName(seriesName)
                .orElseThrow(() -> new RuntimeException("Movies Series not found with name: " + seriesName));

        // Найти эпизод по ID
        Episodes episode = episodesRepository.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found with ID: " + episodeId));

        // Получить все слова, связанные с данным сериалом и эпизодом
        List<Word> words = wordsOfContentRepository.findWordsBySeriesAndEpisode(moviesSeries.getId(), episodeId);

        // Если слов не найдено, возвращаем статус 204 No Content
        if (words.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Возвращаем найденные слова
        return ResponseEntity.ok(words);
    }

    @GetMapping("/find/movie")
    public ResponseEntity<List<Word>> getWordsBySeriesNameAndEpisodeId(
            @RequestParam String movieName) {

        // Найти сериал по его имени
        MoviesSeries moviesSeries = moviesSeriesRepository.findByName(movieName)
                .orElseThrow(() -> new RuntimeException("Movies Series not found with name: " + movieName));


        // Получить все слова, связанные с данным сериалом и эпизодом
        List<Word> words = wordsOfContentRepository.findWordsByMovie(moviesSeries.getId());

        // Если слов не найдено, возвращаем статус 204 No Content
        if (words.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Возвращаем найденные слова
        return ResponseEntity.ok(words);
    }



    // Получить ID фильма по названию
    @GetMapping("/movie")
    public ResponseEntity<Long> getMovieId(@RequestParam String name) {
        Long movieId = moviesSeriesRepository.findMovieIdByName(name);
        if (movieId != null) {
            return ResponseEntity.ok(movieId);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Получить ID сериала, ID сезона и ID серии
    @GetMapping("/series")
    public ResponseEntity<Map<String, Long>> getSeriesSeasonEpisodeIds(
            @RequestParam String name,
            @RequestParam Integer seasonNumber,
            @RequestParam Integer episodeNumber) {

        List<Object[]> result = moviesSeriesRepository.findSeriesSeasonEpisodeIds(name, seasonNumber, episodeNumber);

        if (!result.isEmpty()) {
            Object[] ids = result.get(0);
            Map<String, Long> response = Map.of(
                    "seriesId", (Long) ids[0],
                    "seasonId", (Long) ids[1],
                    "episodeId", (Long) ids[2]
            );
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


}
