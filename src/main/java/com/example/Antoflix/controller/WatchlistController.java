package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.movie.AddMovieToWatchlist;
import com.example.Antoflix.dto.request.movie.AddMovieToWatchlistByName;
import com.example.Antoflix.dto.request.watchlist.AddEmptyWatchlistRequest;
import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.mapper.WatchlistMapper;
import com.example.Antoflix.service.WatchlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
        Watchlist watchlist = watchlistService.createWatchlist(addWatchlistRequest);

        WatchlistResponse response = watchlistMapper.toWatchListResponse(watchlist);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/empty")
    public ResponseEntity<WatchlistResponse> createEmptyWatchlist(@RequestBody AddEmptyWatchlistRequest addEmptyWatchlistRequest){
        Watchlist watchlist = watchlistService.createEmptyWatchlist(addEmptyWatchlistRequest);

        WatchlistResponse response = watchlistMapper.toEmptyWatchlistResponse(watchlist);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{watchlistId}/movies/{movieId}")
    public ResponseEntity<String> addMovieToWatchlist(@PathVariable Integer watchlistId, @PathVariable Integer movieId){
        watchlistService.addMovieToWatchList(watchlistId, movieId);
        //return ResponseEntity.status(HttpStatus.OK).body("Movie added to watchlist!");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addMovieToWatchlist")
    public ResponseEntity<String> addMovieToUserWatchlist(@RequestBody AddMovieToWatchlistByName request){ // Removed the Authentication from method parameters
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        watchlistService.addMovieToUserWatchlist(email, request.getWatchlistName(), request.getMovieId());
        return ResponseEntity.ok("Movie added to watchlist successfully");
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWatchlist(@PathVariable Integer id, Principal principal){
        watchlistService.deleteWatchlist(id, principal);
        return ResponseEntity.ok("Watchlist deleted successfully!");
    }

    @GetMapping("/my")
    public ResponseEntity<List<WatchlistResponse>> getWatchlistForCurrentUser(Principal principal){
        String email = principal.getName();
        List<WatchlistResponse> watchlistResponses = watchlistService.getUsersWatchlist(email);
        return ResponseEntity.ok(watchlistResponses);
    }
}
