package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @PostMapping
    public ResponseEntity<Void> createWatchlist(@RequestBody AddWatchlistRequest addWatchlistRequest){
        watchlistService.createWatchlist(addWatchlistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{watchlistId}/movies/{movieId}")
    public ResponseEntity<String> addMovieToWatchlist(@PathVariable Integer watchlistID, @PathVariable Integer movieId){
        watchlistService.addMovieToWatchList(watchlistID, movieId);
        return ResponseEntity.status(HttpStatus.OK).body("Movie added to watchlist!");
    }

    @DeleteMapping("/{watchlistId}/movies/{movieId}")
    public ResponseEntity<String> removeMovieFromWatchlist(@PathVariable Integer watchlistId, @PathVariable Integer movieId){
        watchlistService.removeMovieFromWatchlist(watchlistId, movieId);
        return ResponseEntity.status(HttpStatus.OK).body("Movie removed from watchlist.");
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchlistResponse> getWatchlistById (@PathVariable Integer watchlistId){
        watchlistService.getWatchlistById(watchlistId);
        return ResponseEntity.ok().build();
    }
}
