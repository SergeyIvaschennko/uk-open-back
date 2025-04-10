package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
public class PostController {
    private MoviesSeriesRepository moviesSeriesRepository;
    private EpisodesRepository episodesRepository;
    private WordsOfContentRepository wordsOfContentRepository;
    private MoviesSeriesMetaRepository moviesSeriesMetaRepository;
    private SeasonsRepository seasonsRepository;

    @Autowired
    public PostController(MoviesSeriesRepository moviesSeriesRepository, EpisodesRepository episodesRepository, WordsOfContentRepository wordsOfContentRepository, MoviesSeriesMetaRepository moviesSeriesMetaRepository, SeasonsRepository seasonsRepository) {
        this.moviesSeriesRepository = moviesSeriesRepository;
        this.episodesRepository = episodesRepository;
        this.wordsOfContentRepository = wordsOfContentRepository;
        this.moviesSeriesMetaRepository = moviesSeriesMetaRepository;
        this.seasonsRepository = seasonsRepository;
    }


    @PostMapping("/moviesseries")
    public ResponseEntity<String> createSeriesWithMeta(@RequestBody MoviesSeries series) {
        // Временно сохраняем мету отдельно
        MoviesSeriesMeta meta = series.getMoviesSeriesMeta();
        series.setMoviesSeriesMeta(null); // отключаем связь, чтобы Hibernate не пытался её сам сохранить

        // Сохраняем сериал
        MoviesSeries savedSeries = moviesSeriesRepository.save(series);

        // Если была мета — сохраняем отдельно и вручную связываем
        if (meta != null) {
            meta.setMoviesSeriesId(savedSeries.getId());
            moviesSeriesMetaRepository.save(meta);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("OK Movieseries successfully saved");
    }

//    {
//        "name": "Back to the Future",
//            "typeOfContentId": 2,
//            "moviesSeriesMeta": {
//        "pic": "https://upload.wikimedia.org/wikipedia/en/d/d2/Back_to_the_Future.jpg",
//                "description": "Marty McFly, a 17-year-old high school student, is accidentally sent 30 years into the past in a time-traveling DeLorean invented by his close friend, the eccentric scientist Doc Brown.",
//                "rating": 8.5,
//                "emoji": "🚗⚡️",
//                "release": "1985-07-03",
//                "year": 1985
//    }
//    }



    @PostMapping("/seasons")
    public ResponseEntity<String> createSeason(@RequestBody Seasons season) {
        // Проверяем, существует ли сериал с переданным ID и соответствует ли его typeOfContentId значению 1
        MoviesSeries moviesSeries = moviesSeriesRepository.findById(season.getMoviesSeriesId())
                .orElse(null);  // Ищем сериал по ID

        if (moviesSeries == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR Movies series not found.");
        }

        if (!moviesSeries.getTypeOfContentId().equals(1L)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR Movies series type is not valid.");
        }

        // Проверяем, существует ли уже сезон с таким номером для данного сериала
        Optional<Seasons> existingSeason = seasonsRepository.findByMoviesSeriesIdAndSeasonNumber(season.getMoviesSeriesId(), season.getSeasonNumber());

        if (existingSeason.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR Season with this number already exists for the given movie series.");
        }

        // Если проверки пройдены, сохраняем сезон
        season.setMoviesSeriesId(moviesSeries.getId());  // Устанавливаем ID сериала в сезон
        seasonsRepository.save(season);  // Сохраняем сезон

        // Возвращаем сообщение об успешном добавлении
        return ResponseEntity.status(HttpStatus.CREATED).body("OK Season successfully saved.");
    }


//    {
//        "moviesSeriesId": 1,
//            "seasonNumber": 1
//    }


    @PostMapping("/episodes")
    public ResponseEntity<String> createEpisode(@RequestBody Episodes episode) {
        // Проверяем, существует ли сезон с переданным ID
        Seasons season = seasonsRepository.findById(episode.getSeasonsId())
                .orElse(null);  // Ищем сезон по ID

        if (season == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR Season not found.");
        }

        // Проверяем, что сериал, к которому относится этот сезон, имеет правильный typeOfContentId
        MoviesSeries moviesSeries = moviesSeriesRepository.findById(season.getMoviesSeriesId())
                .orElse(null);  // Ищем сериал по ID

        if (moviesSeries == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR Movies series not found.");
        }

        if (!moviesSeries.getTypeOfContentId().equals(1L)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR Movies series type is not valid. Expected typeId: 1.");
        }

        // Проверяем, существует ли уже эпизод с таким номером в этом сезоне
        Optional<Episodes> existingEpisode = episodesRepository.findBySeasonsIdAndEpisodeNumber(episode.getSeasonsId(), episode.getEpisodeNumber());

        if (existingEpisode.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR Episode with this number already exists in the given season.");
        }

        // Если все проверки пройдены, сохраняем новый эпизод
        episodesRepository.save(episode);  // Сохраняем новый эпизод

        // Возвращаем сообщение об успешном добавлении
        return ResponseEntity.status(HttpStatus.CREATED).body("OK Episode successfully saved.");
    }







}
