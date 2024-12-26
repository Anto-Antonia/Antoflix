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
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY) //added fetchType lazy
    @JoinTable(name = "watchlist_movies",
                joinColumns = {@JoinColumn(name = "watchlist_id")},
                inverseJoinColumns = {@JoinColumn(name = "movie_id")})
    private List<Movie> movies = new ArrayList<>();
}
