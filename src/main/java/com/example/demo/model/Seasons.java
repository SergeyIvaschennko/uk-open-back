package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seasons")
public class Seasons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movies_series_id", nullable = false)
    private Long moviesSeriesId;

    @Column(nullable = false)
    private Integer seasonNumber;

    @ManyToOne
    @JoinColumn(name = "movies_series_id", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private MoviesSeries moviesSeries;

    @OneToMany(mappedBy = "seasons", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Episodes> episodes;
}
