package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.genre.AddGenreRequest;
import com.example.Antoflix.dto.request.movie.AddMovieRequest;
import com.example.Antoflix.dto.request.movie.UpdateMovieRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.exceptions.genre.AddGenreException;
import com.example.Antoflix.exceptions.movie.AddMovieException;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.repository.GenreRepository;
import com.example.Antoflix.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MovieGenreServiceImpl implements MovieGenreService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final MovieGenreMapper movieGenreMapper;

    public MovieGenreServiceImpl(MovieRepository movieRepository, GenreRepository genreRepository, MovieGenreMapper movieGenreMapper) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.movieGenreMapper = movieGenreMapper;
    }

    @Override
    public Movie addMovie(AddMovieRequest addMovieRequest) {
        Movie movie = movieGenreMapper.fromAddMovieRequest(addMovieRequest);
        return movieRepository.save(movie);
    }

    @Override
    public Genre addGenre(AddGenreRequest addGenreRequest) {
        Genre genre = movieGenreMapper.fromAddGenreRequest(addGenreRequest);
        return genreRepository.save(genre);
    }

    @Override
    public MovieResponse getMovieById(Integer id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if(optionalMovie.isPresent()){
            Movie movie = optionalMovie.get();
            MovieResponse movieResponse = movieGenreMapper.fromMovieResponse(movie);

            return movieResponse;
        } else{
            throw new AddMovieException("Movie not found");
        }
    }

    @Override
    public void updateMovieGenres(Integer id, List<Integer> genreId) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new AddMovieException("Movie not found"));

        List<Genre> genres = genreRepository.findAllById(genreId);
        movie.setGenres(genres);
        movieRepository.save(movie);
    }

    @Override
    public MovieResponse removeGenreFromMovie(Integer movieId, Integer genreId) {

       Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new AddMovieException("Movie not found"));
       Genre genre =  genreRepository.findById(genreId).orElseThrow(()-> new AddGenreException("Genre not found"));

       movie.getGenres().remove(genre);
       movieRepository.save(movie);

       return movieGenreMapper.fromMovieResponse(movie);
    }

    @Override
    public void updateMovie(Integer id, UpdateMovieRequest updateMovieRequest) {
        Optional<Movie> movieOptional = movieRepository.findById(id);

        if(movieOptional.isPresent()){
            Movie movieToUpdate = movieOptional.get();
            movieToUpdate.setTitle(updateMovieRequest.getTitle());
            movieToUpdate.setDescription(updateMovieRequest.getDescription());

            movieRepository.save(movieToUpdate);
        } else{
            throw new AddMovieException("The movie with id " + id + " does not exist");
        }
    }

    @Override
    public void deleteMovie(Integer id) {
        if(!movieRepository.existsById(id)){
            throw new AddMovieException("Movie with id " + id + " does not exist");
        }
        movieRepository.deleteById(id);

    }

    @Transactional
    @Override
    public void deleteGenre(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id " + id + " not found"));
        for (Movie movie : genre.getMovies()){
            movie.getGenres().remove(genre);
            movieRepository.save(movie);
        }
        genreRepository.deleteById(id);
    }

    @Override
    public List<MovieResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieResponse> movieResponses = movies.stream()
                .map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());

        return movieResponses;
    }

    // added method to return movies alphabetically
    @Override
    public List<MovieResponse> getAllMoviesAsc() {
        List<Movie> movies = movieRepository.findAllByOrderByTitleAsc();
        List<MovieResponse> movieResponses = movies.stream()
                .map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());

        return movieResponses;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreResponse> genreResponses = genres.stream()
                .map(movieGenreMapper::fromGenreResponse).collect(Collectors.toList());

        return genreResponses;
    }

    @Override
    public List<MovieResponse> getMoviesByGenre(String genreName) {
        List<Movie> movies = movieRepository.findByGenres_IgnoreCase(genreName);
        List<MovieResponse> responses = movies.stream().map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());

        return responses;
    }

    @Override
    public List<MovieResponse> getRecentMovies(int count) {
        Pageable pageable = PageRequest.of(0, count);
        List<Movie> movies = movieRepository.findRecentMovie(pageable);
        List<MovieResponse> responses = movies.stream()
                .map(movieGenreMapper::fromMovieResponse).collect(Collectors.toList());

        return responses;
    }


}
