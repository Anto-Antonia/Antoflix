package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Watchlist;

import java.security.Principal;

public interface WatchlistService {
    Watchlist createWatchlist(AddWatchlistRequest addWatchlistRequest);
    void addMovieToWatchList(Integer watchlistId, Integer movieId);
    void removeMovieFromWatchlist(Integer watchlistId, Integer movieId);
    WatchlistResponse getWatchlistById(Integer watchlistId);
    void deleteWatchlist(Integer watchlistId, Principal principal);
}
