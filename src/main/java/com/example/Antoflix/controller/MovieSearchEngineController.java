package com.example.Antoflix.controller;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.service.MovieSearchEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/movieSearch")
public class MovieSearchEngineController {
    private final MovieSearchEngineService movieSearchEngineService;

    public MovieSearchEngineController(MovieSearchEngineService movieSearchEngineService) {
        this.movieSearchEngineService = movieSearchEngineService;
    }

    @GetMapping("/keyword")
    public ResponseEntity<List<MovieResponse>> searchMovieByKeyword(@RequestParam String keyword){
        List<MovieResponse> movieResponses = movieSearchEngineService.searchMovieByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK).body(movieResponses);
    }

    @GetMapping("/title")
    public ResponseEntity<List<MovieResponse>> searchMovieByTitle(@RequestParam String title){
        List<MovieResponse> movieResponses = movieSearchEngineService.searchMovieByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(movieResponses);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<MovieResponse>> searchMovieByGenre(@RequestParam String genre){
        List<MovieResponse> movieResponses = movieSearchEngineService.searchMovieByGenre(genre);
        return ResponseEntity.status(HttpStatus.OK).body(movieResponses);
    }
}
