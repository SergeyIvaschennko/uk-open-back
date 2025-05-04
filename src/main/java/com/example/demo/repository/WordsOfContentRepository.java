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

    @Query("SELECT w.word FROM WordsOfContent w WHERE w.moviesSeriesId = :moviesSeriesId")
    List<Word> findWordsByMovie(@Param("moviesSeriesId") Long moviesSeriesId);

    @Query("""
    SELECT woc.word
    FROM WordsOfContent woc
    WHERE woc.moviesSeriesId = :movieId
      AND (:level IS NULL OR LOWER(woc.word.level) = LOWER(CAST(:level AS string)))
      AND (:partOfSpeech IS NULL OR LOWER(woc.word.partOfSpeech) = LOWER(CAST(:partOfSpeech AS string)))
""")
    List<Word> findWordsByMovieAndFilters(@Param("movieId") Long movieId,
                                          @Param("level") String level,
                                          @Param("partOfSpeech") String partOfSpeech);

    @Query("""
        SELECT woc.word
        FROM WordsOfContent woc
        WHERE woc.moviesSeriesId = :movieId
          AND woc.word.partOfSpeech = 'expression'
    """)
    List<Word> findExpressionsByMovieId(@Param("movieId") Long movieId);

    @Query("""
        SELECT woc.word
        FROM WordsOfContent woc
        WHERE woc.moviesSeriesId = :movieId
          AND woc.episodeId = :episodeId
          AND (:level IS NULL OR LOWER(woc.word.level) = LOWER(CAST(:level AS string)))
          AND (:partOfSpeech IS NULL OR LOWER(woc.word.partOfSpeech) = LOWER(CAST(:partOfSpeech AS string)))
    """)
    List<Word> findWordsByEpisodeAndFilters(@Param("movieId") Long movieId,
                                            @Param("episodeId") Long episodeId,
                                            @Param("level") String level,
                                            @Param("partOfSpeech") String partOfSpeech);

    @Query("""
    SELECT woc.word
    FROM WordsOfContent woc
    WHERE woc.moviesSeriesId = :movieId
      AND woc.episodeId = :episodeId
      AND woc.word.partOfSpeech = 'expression'
""")
    List<Word> findExpressionsByMovieIdAndEpisodeId(
            @Param("movieId") Long movieId,
            @Param("episodeId") Long episodeId
    );

}

