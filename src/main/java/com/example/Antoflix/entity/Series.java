package com.example.Antoflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    private String releaseYear;

    @ManyToMany
    @JoinTable(name = "series_genre",
                joinColumns = {
                    @JoinColumn(name = "series_id"),
                    @JoinColumn(name = "series_title", referencedColumnName = "title"),
                },
                inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<Season> seasons = new ArrayList<>();
}
