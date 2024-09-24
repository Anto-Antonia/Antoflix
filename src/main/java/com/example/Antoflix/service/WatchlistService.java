package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Watchlist;

public interface WatchlistService {
    public Watchlist createWatchlist(AddWatchlistRequest addWatchlistRequest);
    public void addMovieToWatchList(Integer watchlistId, Integer movieId);
    public void removeMovieFromWatchlist(Integer watchListId, Integer movieId);
    public WatchlistResponse getWatchlistById(Integer watchlistId);
}
