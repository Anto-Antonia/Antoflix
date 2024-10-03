package com.example.Antoflix.service;

import com.example.Antoflix.dto.response.movie.MovieResponse;

import java.util.List;

public interface MovieSearchEngineService {
    List<MovieResponse> searchMovieByKeyword(String keyword);
    List<MovieResponse> searchMovieByTitle(String title);
    List<MovieResponse> searchMovieByGenre(String genre);
}
