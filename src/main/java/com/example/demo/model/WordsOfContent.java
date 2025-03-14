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

    @Column(name = "episode_id", nullable = false)
    private Long episodeId;

    @Column(name = "movies_series_id", nullable = false)
    private Long moviesSeriesId;

    @Column(name = "word_id", nullable = false)
    private Long wordId;

    @ManyToOne
    @JoinColumn(name = "episode_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Episodes episode;

    @ManyToOne
    @JoinColumn(name = "movies_series_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MoviesSeries moviesSeries;

    @ManyToOne
    @JoinColumn(name = "word_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Word word;
}



