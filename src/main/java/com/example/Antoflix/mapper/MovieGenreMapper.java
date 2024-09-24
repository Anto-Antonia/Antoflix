package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.genre.AddGenreRequest;
import com.example.Antoflix.dto.request.movie.AddMovieRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieGenreMapper {

    @Autowired
    private GenreRepository genreRepository;

    public Movie fromAddMovieRequest(AddMovieRequest addMovieRequest){
        Movie movie = new Movie();
        movie.setTitle(addMovieRequest.getTitle());
        movie.setDescription(addMovieRequest.getDescription());
        movie.setReleaseDate(addMovieRequest.getReleaseDate());

        List<Genre> genres = genreRepository.findAllById(addMovieRequest.getGenreId());
        movie.setGenres(genres);

        return movie;
    }
    public MovieResponse fromMovieResponse(Movie movie){
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setTitle(movie.getTitle());
        movieResponse.setDescription(movie.getDescription());
        movieResponse.setLocalDate(movie.getReleaseDate());

        List<GenreResponse> genreResponses = movie.getGenres().stream()
                .map(this:: fromGenreResponse)
                .collect(Collectors.toList());
        movieResponse.setGenres(genreResponses);

        return movieResponse;
    }

    public Genre fromAddGenreRequest(AddGenreRequest addGenreRequest){
        Genre genre = new Genre();
        genre.setGenreName(addGenreRequest.getName());

        return genre;
    }

    public GenreResponse fromGenreResponse(Genre genre){
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setName(genre.getGenreName());

        return genreResponse;
    }
}
