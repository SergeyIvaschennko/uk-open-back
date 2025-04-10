package com.example.demo.repository;

import com.example.demo.model.Seasons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SeasonsRepository extends JpaRepository<Seasons, Long> {

    @Query("SELECT s FROM Seasons s WHERE s.moviesSeries.id = :seriesId")
    List<Seasons> findByMoviesSeriesId(@Param("seriesId") Long seriesId);

    Optional<Seasons> findByMoviesSeriesIdAndSeasonNumber(Long moviesSeriesId, Integer seasonNumber);
}
