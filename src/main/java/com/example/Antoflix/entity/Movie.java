package com.example.Antoflix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private String description;

    @ManyToMany(mappedBy = "favoriteMovie")
    private List<User> favoritedByUsed = new ArrayList<>();

    @ManyToMany(mappedBy = "movies")
    private List<Watchlist> includedInWatchlist = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "movie_genre",
                joinColumns = @JoinColumn(name = "movie_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres = new ArrayList<>();
}
