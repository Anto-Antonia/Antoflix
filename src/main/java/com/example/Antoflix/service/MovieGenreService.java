package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.genre.AddGenreRequest;
import com.example.Antoflix.dto.request.movie.AddMovieRequest;
import com.example.Antoflix.dto.request.movie.UpdateMovieRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Movie;

import java.util.List;

public interface MovieGenreService {
    public Movie addMovie(AddMovieRequest addMovieRequest);
    public Genre addGenre(AddGenreRequest addGenreRequest);
    public MovieResponse getMovieById(Integer id);
    public void updateMovieGenres(Integer id, List<Integer> genreId);
    public MovieResponse removeGenreFromMovie(Integer movieId, Integer genreId);
    public void updateMovie(Integer id, UpdateMovieRequest updateMovieRequest);
    public void deleteMovie(Integer id);
    public void deleteGenre(Integer id);
    public List<MovieResponse> getAllMovies();
    List<MovieResponse> getAllMoviesAsc();
    public List<GenreResponse> getAllGenres();
    List<MovieResponse> getMoviesByGenre(String genreName);

}
