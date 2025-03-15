package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies_series_meta")
public class MoviesSeriesMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movies_series_id", nullable = false)
    private Long moviesSeriesId;

    private String pic;

    private String description;

    private Float rating;

    private String emoji;

    private String release;

    @OneToOne
    @JoinColumn(name = "movies_series_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private MoviesSeries moviesSeries;
}

