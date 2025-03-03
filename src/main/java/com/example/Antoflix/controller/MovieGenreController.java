package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.genre.AddGenreRequest;
import com.example.Antoflix.dto.request.movie.AddMovieRequest;
import com.example.Antoflix.dto.request.movie.UpdateMovieRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.service.MovieGenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @PostMapping("/movie")
    public ResponseEntity<Void> addMovie(@RequestBody AddMovieRequest addMovieRequest){
        movieGenreService.addMovie(addMovieRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/genre")
    public ResponseEntity<Void> addGenre(@RequestBody AddGenreRequest addGenreRequest){
        movieGenreService.addGenre(addGenreRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Integer id){
        MovieResponse movieResponse = movieGenreService.getMovieById(id);
        return ResponseEntity.status(HttpStatus.OK).body(movieResponse);
    }

    @PatchMapping("/genre/{id}")
    public ResponseEntity<String> updateMovieGenres(@PathVariable Integer id, @RequestBody List<Integer> genreId){
        movieGenreService.updateMovieGenres(id, genreId);
        return ResponseEntity.ok("Movie genres updated successfully");
    }

    @DeleteMapping("/{movieId}/genres/{genreId}")
    public ResponseEntity<MovieResponse> removeGenreFromMovie(@PathVariable Integer movieId, @PathVariable Integer genreId){
        MovieResponse movieResponse = movieGenreService.removeGenreFromMovie(movieId, genreId);
        return ResponseEntity.ok().body(movieResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMovie(@PathVariable Integer id, @RequestBody @Valid UpdateMovieRequest updateMovieRequest){
       movieGenreService.updateMovie(id, updateMovieRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id){
        movieGenreService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/genre/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id){
        movieGenreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovie(){
        List<MovieResponse> movieResponseList = movieGenreService.getAllMovies();
        return ResponseEntity.status(HttpStatus.OK).body(movieResponseList);
    }

    @GetMapping("/asc")
    public ResponseEntity<List<MovieResponse>> getAllMoviesAsc(){
        List<MovieResponse> movieResponses = movieGenreService.getAllMoviesAsc();
        return ResponseEntity.status(HttpStatus.OK).body(movieResponses);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreResponse>> getAllGenres(){
        List<GenreResponse> genreResponseList = movieGenreService.getAllGenres();
        return ResponseEntity.status(HttpStatus.OK).body(genreResponseList);
    }

    @GetMapping("/movie/{genreName}")
    public ResponseEntity<List<MovieResponse>> getMoviesByGenre(@PathVariable String genreName){
        List<MovieResponse> responses = movieGenreService.getMoviesByGenre(genreName);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
