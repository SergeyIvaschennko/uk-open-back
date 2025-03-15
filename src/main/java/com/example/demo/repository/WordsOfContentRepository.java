package com.example.demo.repository;

import com.example.demo.model.Word;
import com.example.demo.model.WordsOfContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordsOfContentRepository extends JpaRepository<WordsOfContent, Long> {

    @Query("SELECT w.word FROM WordsOfContent w WHERE w.moviesSeriesId = :moviesSeriesId AND w.episodeId = :episodeId")
    List<Word> findWordsBySeriesAndEpisode(@Param("moviesSeriesId") Long moviesSeriesId, @Param("episodeId") Long episodeId);
}

