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
@Table(name = "movies_series")
public class MoviesSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "type_of_content_id", nullable = false)
    private Long typeOfContentId;

    @ManyToOne
    @JoinColumn(name = "type_of_content_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TypeOfContent typeOfContent;

    @OneToOne(mappedBy = "moviesSeries")
    private MoviesSeriesMeta moviesSeriesMeta;
}
