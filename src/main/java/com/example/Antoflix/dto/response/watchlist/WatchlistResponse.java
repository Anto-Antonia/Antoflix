package com.example.Antoflix.dto.response.watchlist;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistResponse {
    private String name;
    private List<MovieResponse> movies;
    private Integer userId; // user's id
}
