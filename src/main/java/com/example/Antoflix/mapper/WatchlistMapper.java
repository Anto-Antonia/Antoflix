package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.entity.Watchlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WatchlistMapper {
    @Autowired
    private MovieGenreMapper movieGenreMapper;

    public Watchlist createWatchlistRequest(AddWatchlistRequest addWatchlistRequest, List<Movie> movies, User user){
        Watchlist watchlist = new Watchlist();
        watchlist.setName(addWatchlistRequest.getName());
        watchlist.setMovies(movies);
        watchlist.setUser(user); //setting a user to the watchlist

        return watchlist;
    }

    public WatchlistResponse toWatchListResponse(Watchlist watchlist){
        WatchlistResponse watchlistResponse = new WatchlistResponse();
        watchlistResponse.setName(watchlist.getName());

        List<MovieResponse> movies = watchlist.getMovies().stream()
                .map(movieGenreMapper::fromMovieResponse)
                .collect(Collectors.toList());

        watchlistResponse.setMovies(movies);

        return watchlistResponse;
    }
}
