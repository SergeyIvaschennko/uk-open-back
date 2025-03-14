package com.example.demo.repository;

import com.example.demo.model.MoviesSeries;
import com.example.demo.model.TypeOfContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeOfContentRepository extends JpaRepository<TypeOfContent, Long> {

}

