package com.example.demo.repository;

import com.example.demo.model.MoviesSeries;
import com.example.demo.model.MoviesSeriesMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesSeriesMetaRepository extends JpaRepository<MoviesSeriesMeta, Long> {
}
