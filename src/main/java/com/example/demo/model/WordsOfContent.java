package com.example.demo.model;

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
@Table(name = "words_of_content")
public class WordsOfContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private Episodes episode;

    @ManyToOne
    @JoinColumn(name = "movies_series_id", nullable = false)
    private MoviesSeries moviesSeries;

    @ManyToOne
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;
}

