package com.example.demo.repository;

import com.example.demo.model.MoviesSeries;
import com.example.demo.model.MoviesSeriesMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesSeriesRepository extends JpaRepository<MoviesSeries, Long> {




    @Query("SELECT m FROM MoviesSeries m JOIN m.moviesSeriesMeta msm WHERE m.typeOfContentId = :typeOfContentId ORDER BY msm.rating DESC")
    List<MoviesSeries> findTopRatedSeriesByTypeOfContent(@Param("typeOfContentId") Long typeOfContentId);




    @Query("SELECT m.moviesSeriesMeta FROM MoviesSeries m WHERE m.id = :seriesId")
    MoviesSeriesMeta findMetaBySeriesId(@Param("seriesId") Long seriesId);

    @Query("SELECT m FROM MoviesSeries m WHERE m.name = :name")
    Optional<MoviesSeries> findByName(@Param("name") String name);



    // Получить ID фильма по названию (если это фильм)
    @Query("SELECT m.id FROM MoviesSeries m WHERE m.name = :name AND m.typeOfContentId = 2")
    Long findMovieIdByName(@Param("name") String name);

    // Получить ID сериала, ID сезона и ID серии по названию сериала, номеру сезона и номеру серии
    @Query("""
        SELECT m.id, s.id, e.id 
        FROM MoviesSeries m
        JOIN Seasons s ON s.moviesSeriesId = m.id
        JOIN Episodes e ON e.seasonsId = s.id
        WHERE m.name = :name AND s.seasonNumber = :seasonNumber AND e.episodeNumber = :episodeNumber
    """)
    List<Object[]> findSeriesSeasonEpisodeIds(
            @Param("name") String name,
            @Param("seasonNumber") Integer seasonNumber,
            @Param("episodeNumber") Integer episodeNumber
    );


    @Query("SELECT m FROM MoviesSeries m JOIN m.moviesSeriesMeta msm WHERE msm.year = 2011 OR msm.year = 2019 ORDER BY msm.rating DESC")
    List<MoviesSeries> findMoviesReleasedIn2024Or2025();
}
