package com.example.demo.repository;

import com.example.demo.model.MoviesSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesSeriesRepository extends JpaRepository<MoviesSeries, Long> {


//    @Query("SELECT m FROM MoviesSeries m WHERE m.typeOfContentId = 1")
//    List<MoviesSeries> findTopRatedSeries();
    Optional<MoviesSeries> findById(Long id);



//    @Query("SELECT m FROM MoviesSeries m WHERE m.typeOfContentId = 3 ORDER BY m.moviesSeriesMeta.rating DESC")
//    List<MoviesSeries> findTopRatedCartoons();
//
//    @Query("SELECT m FROM MoviesSeries m WHERE m.moviesSeriesMeta.release LIKE '2024%'")
//    List<MoviesSeries> findMoviesByYear(int year);
}
