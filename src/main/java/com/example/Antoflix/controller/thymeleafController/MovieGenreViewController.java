package com.example.Antoflix.controller.thymeleafController;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.service.MovieGenreServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class MovieGenreViewController {
    private final MovieGenreServiceImpl movieGenreService;

    public MovieGenreViewController(MovieGenreServiceImpl movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping("/{genreName}")
    public String getMoviesByGenre(@PathVariable String genreName, Model model){
        List<MovieResponse> movies = movieGenreService.getMoviesByGenre(genreName);
        System.out.println("Movies: " + movies); // Log movies to console
        model.addAttribute("genreName", genreName); // Pass genre name to the view
        model.addAttribute("movies", movies); // Pass movies to the view
        return "genre-movies"; // Return the 'movies.html' template
    }
}
