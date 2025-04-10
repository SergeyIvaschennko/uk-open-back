package com.example.demo.repository;

import com.example.demo.model.Episodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EpisodesRepository extends JpaRepository<Episodes, Long> {
    Optional<Episodes> findBySeasonsIdAndEpisodeNumber(Long seasonsId, Integer episodeNumber);
}

