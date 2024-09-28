package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.mapper.WatchlistMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchlistServiceImpl implements WatchlistService{

    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final MovieRepository movieRepository;

    public WatchlistServiceImpl(WatchlistRepository watchlistRepository, WatchlistMapper watchlistMapper, MovieRepository movieRepository) {
        this.watchlistRepository = watchlistRepository;
        this.watchlistMapper = watchlistMapper;
        this.movieRepository = movieRepository;
    }

    @Override
    public Watchlist createWatchlist(AddWatchlistRequest addWatchlistRequest) {
        Watchlist watchlist = watchlistMapper.addWatchlistRequest(addWatchlistRequest);
        return watchlistRepository.save(watchlist);
    }

    @Override
    public void addMovieToWatchList(Integer watchlistId, Integer movieId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId).orElseThrow(()-> new IllegalArgumentException("Watchlist not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new IllegalArgumentException("Movie not found"));

        watchlist.getMovies().add(movie);
        watchlistRepository.save(watchlist);
    }

    @Override
    public void removeMovieFromWatchlist(Integer watchlistId, Integer movieId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId).orElseThrow(()-> new IllegalArgumentException("Watchlist not found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new IllegalArgumentException("Movie not found"));

        watchlist.getMovies().remove(movie);
        watchlistRepository.save(watchlist);

    }

    @Override
    public WatchlistResponse getWatchlistById(Integer watchlistId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId).orElseThrow(()-> new IllegalArgumentException("Watchlist not found"));

        return watchlistMapper.toWatchListResponse(watchlist);
    }
}
