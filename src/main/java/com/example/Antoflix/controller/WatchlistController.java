package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.mapper.WatchlistMapper;
import com.example.Antoflix.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {
    private final WatchlistService watchlistService;
    private final WatchlistMapper watchlistMapper;

    public WatchlistController(WatchlistService watchlistService, WatchlistMapper watchlistMapper) {
        this.watchlistService = watchlistService;
        this.watchlistMapper = watchlistMapper;
    }

    @PostMapping
    public ResponseEntity<WatchlistResponse> createWatchlist(@RequestBody AddWatchlistRequest addWatchlistRequest){ // 'principal.getName()' provides the username of the authenticated user
        watchlistService.createWatchlist(addWatchlistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{watchlistId}/movies/{movieId}")
    public ResponseEntity<String> addMovieToWatchlist(@PathVariable Integer watchlistId, @PathVariable Integer movieId){
        watchlistService.addMovieToWatchList(watchlistId, movieId);
        //return ResponseEntity.status(HttpStatus.OK).body("Movie added to watchlist!");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{watchlistId}/movies/{movieId}")
    public ResponseEntity<String> removeMovieFromWatchlist(@PathVariable Integer watchlistId, @PathVariable Integer movieId){
        watchlistService.removeMovieFromWatchlist(watchlistId, movieId);
        return ResponseEntity.status(HttpStatus.OK).body("Movie removed from watchlist.");
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchlistResponse> getWatchlistById (@PathVariable Integer watchlistId){
       WatchlistResponse watchlistResponse = watchlistService.getWatchlistById(watchlistId);
        return ResponseEntity.ok(watchlistResponse);
    }
}
