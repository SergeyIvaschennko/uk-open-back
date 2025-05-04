package com.example.demo.repository;

import com.example.demo.model.MoviesSeries;
import com.example.demo.model.MoviesSeriesMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoviesSeriesRepository extends JpaRepository<MoviesSeries, Long> {


    @Query("SELECT m FROM MoviesSeries m JOIN m.moviesSeriesMeta msm WHERE msm.year = 2024 OR msm.year = 2025 ORDER BY msm.rating DESC LIMIT 10")
    List<MoviesSeries> findMoviesReleasedIn2024Or2025();


    @Query(value = """
    SELECT ms.* FROM movies_series ms
    JOIN movies_series_meta meta ON meta.movies_series_id = ms.id
    WHERE ms.type_of_content_id = :typeId
      AND meta.rating >= 8.5
    ORDER BY RANDOM()
    LIMIT 10
    """, nativeQuery = true)
    List<MoviesSeries> findRandomTopRatedByType(Long typeId);

//    @Query(value = """
//        SELECT * FROM movies_series
//        WHERE similarity(name, :query) > 0.3
//        ORDER BY similarity(name, :query) DESC
//        LIMIT 10
//    """, nativeQuery = true)
//    List<MoviesSeries> searchBySimilarName(@Param("query") String query);


    @Query("SELECT m FROM MoviesSeries m WHERE m.name ILIKE CONCAT('%', :query, '%') ORDER BY m.name")
    List<MoviesSeries> searchBySimilarName(@Param("query") String query);


}
