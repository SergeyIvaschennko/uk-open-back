package com.example.demo.controller;

import com.example.demo.model.Word;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class WordsController {
    private MoviesSeriesRepository moviesSeriesRepository;
    private EpisodesRepository episodesRepository;
    private WordsOfContentRepository wordsOfContentRepository;

    @Autowired
    public WordsController(MoviesSeriesRepository moviesSeriesRepository, EpisodesRepository episodesRepository, WordsOfContentRepository wordsOfContentRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
        this.episodesRepository = episodesRepository;
        this.wordsOfContentRepository = wordsOfContentRepository;
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
}
