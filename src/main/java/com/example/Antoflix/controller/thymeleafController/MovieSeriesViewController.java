package com.example.Antoflix.controller.thymeleafController;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.service.MovieGenreServiceImpl;
import com.example.Antoflix.service.SeriesSeasonEpisodeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class MovieSeriesViewController {
    private final MovieGenreServiceImpl movieGenreService;
    private final SeriesSeasonEpisodeServiceImpl service;

    public MovieSeriesViewController(MovieGenreServiceImpl movieGenreService, SeriesSeasonEpisodeServiceImpl service) {
        this.movieGenreService = movieGenreService;
        this.service = service;
    }

    @GetMapping("/movies")
    public String getAllMovies(Model model){
        List<MovieResponse> responses = movieGenreService.getAllMovies();
        model.addAttribute("movies", responses);
        return "movies-list";
    }

    @GetMapping("/series")
    public String getAllSeries(Model model){
        List<SeriesResponse> responses = service.getAllSeries();
        model.addAttribute("series", responses);
        return "series-list";
    }
}
