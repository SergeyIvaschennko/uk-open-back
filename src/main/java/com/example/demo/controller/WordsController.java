package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class WordsController {
    private MoviesSeriesRepository moviesSeriesRepository;
    private EpisodesRepository episodesRepository;
    private WordsOfContentRepository wordsOfContentRepository;
    private SeasonsRepository seasonsRepository;

    @Autowired
    public WordsController(MoviesSeriesRepository moviesSeriesRepository, EpisodesRepository episodesRepository, WordsOfContentRepository wordsOfContentRepository, SeasonsRepository seasonsRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
        this.episodesRepository = episodesRepository;
        this.wordsOfContentRepository = wordsOfContentRepository;
        this.seasonsRepository = seasonsRepository;
    }

    //слова для определенного эпизода сериала FILM
    @GetMapping("/find/series")
    public ResponseEntity<List<Word>> getWordsBySeriesIdAndEpisodeId(
            @RequestParam Long seriesId,
            @RequestParam Long episodeId) {

        // Проверка: существует ли сериал с таким ID
        moviesSeriesRepository.findById(seriesId)
                .orElseThrow(() -> new RuntimeException("Movies Series not found with ID: " + seriesId));

        // Проверка: существует ли эпизод с таким ID
        episodesRepository.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found with ID: " + episodeId));

        // Получить все слова, связанные с сериалом и эпизодом
        List<Word> words = wordsOfContentRepository.findWordsBySeriesAndEpisode(seriesId, episodeId);

        if (words.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(words);
    }

    //слова для определенного фильма FILM
    @GetMapping("/find/movie")
    public ResponseEntity<List<Word>> getWordsBySeriesNameAndEpisodeId(
            @RequestParam Long movieId) {

        // Проверка: существует ли фильм с таким ID
        moviesSeriesRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));


        // Получить все слова, связанные с данным сериалом и эпизодом
        List<Word> words = wordsOfContentRepository.findWordsByMovie(movieId);

        // Если слов не найдено, возвращаем статус 204 No Content
        if (words.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Возвращаем найденные слова
        return ResponseEntity.ok(words);
    }

    @GetMapping("/find/fltr/movie")
    public ResponseEntity<List<Word>> getWordsByMovie(
            @RequestParam Long movieId,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String partOfSpeech) {

        moviesSeriesRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        List<Word> words = wordsOfContentRepository.findWordsByMovieAndFilters(movieId, level, partOfSpeech);

        if (words.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(words);
    }

    @GetMapping("/find/expressions")
    public List<Word> getExpressionsByMovieId(@RequestParam Long movieId) {
        return wordsOfContentRepository.findExpressionsByMovieId(movieId);
    }

    @GetMapping("/find/fltr/series")
    public ResponseEntity<List<Word>> getWords(
            @RequestParam Long movieId,
            @RequestParam Long episodeId,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String partOfSpeech
    ) {
        List<Word> words = wordsOfContentRepository.findWordsByEpisodeAndFilters(movieId, episodeId, level, partOfSpeech);

        return ResponseEntity.ok(words);
    }

    @GetMapping("/find/fltr/series/test")
    public ResponseEntity<List<Word>> getWordsTest(
            @RequestParam Long movieId,
            @RequestParam Integer seasonNumber,
            @RequestParam Integer episodeNumber,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String partOfSpeech
    ) {
        // Шаг 1: Найти seasonsId
        Long seasonsId = seasonsRepository.findSeasonIdByMovieIdAndSeasonNumber(movieId, seasonNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Сезон не найден"));

        // Шаг 2: Найти episodeId
        Long episodeId = episodesRepository.findEpisodeIdBySeasonIdAndEpisodeNumber(seasonsId, episodeNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Эпизод не найден"));

        // Шаг 3: Найти список слов
        List<Word> words = wordsOfContentRepository.findWordsByEpisodeAndFilters(movieId, episodeId, level, partOfSpeech);

        return ResponseEntity.ok(words);
    }

    @GetMapping("/find/series/expressions")
    public List<Word> getExpressionsByMovieIdandEpisodeId(@RequestParam Long movieId, @RequestParam Long episodeId) {
        return wordsOfContentRepository.findExpressionsByMovieIdAndEpisodeId(movieId, episodeId);
    }


}
