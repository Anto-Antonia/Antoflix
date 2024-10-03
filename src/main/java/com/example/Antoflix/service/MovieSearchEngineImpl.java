package com.example.Antoflix.service;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.service.MovieSearchEngineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieSearchEngineImpl implements MovieSearchEngineService {

        private final MovieRepository movieRepository;
        private final MovieGenreMapper movieGenreMapper;

    public MovieSearchEngineImpl(MovieRepository movieRepository, MovieGenreMapper movieGenreMapper) {
        this.movieRepository = movieRepository;
        this.movieGenreMapper = movieGenreMapper;
    }


    @Override
    public List<MovieResponse> searchMovieByKeyword(String keyword) {
        List<Movie> movies = movieRepository.findByTitleContainingKeyword(keyword);
        return movies.stream().map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> searchMovieByTitle(String title) {
        List<Movie> movies = movieRepository.findByTitleIgnoreCase(title);
        return movies.stream().map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponse> searchMovieByGenre(String genre) {
        List<Movie> movies = movieRepository.findByGenre(genre);
        return movies.stream().map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());
    }
}
