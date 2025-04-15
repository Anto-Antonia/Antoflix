package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.watchlist.AddEmptyWatchlistRequest;
import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.mapper.WatchlistMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.repository.UserRepository;
import com.example.Antoflix.repository.WatchlistRepository;
import com.example.Antoflix.service.security.CustomUserDetailsService;
import com.example.Antoflix.service.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements WatchlistService{

    private final WatchlistRepository watchlistRepository;
    private final WatchlistMapper watchlistMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public WatchlistServiceImpl(WatchlistRepository watchlistRepository, WatchlistMapper watchlistMapper, MovieRepository movieRepository, UserRepository userRepository) {
        this.watchlistRepository = watchlistRepository;
        this.watchlistMapper = watchlistMapper;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Watchlist createWatchlist(AddWatchlistRequest addWatchlistRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        List<Movie> movies = movieRepository.findAllById(addWatchlistRequest.getMovieId());
            if(movies.isEmpty()){
                throw new RuntimeException("No movies found with the provided IDs");
            }

        Watchlist watchlist = watchlistMapper.createWatchlistRequest(addWatchlistRequest,movies, user);

        return watchlistRepository.save(watchlist);
//        User user = userRepository.findUserByUsername(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        List<Movie> movies = movieRepository.findAllById(addWatchlistRequest.getMovieId());
//        if (movies.isEmpty()) {
//            throw new RuntimeException("No movies found with the provided IDs");
//        }
//
//        // Pass the user to the mapper
//        Watchlist watchlist = watchlistMapper.createWatchlistRequest(addWatchlistRequest, movies, user);
//       // watchlist.setUser(user); // added this <--
//
//        return watchlistRepository.save(watchlist);
    }

    @Override
    public Watchlist createEmptyWatchlist(AddEmptyWatchlistRequest addEmptyWatchlistRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        Watchlist watchlist = watchlistMapper.createEmptyWatchlistRequest(addEmptyWatchlistRequest, user);

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

    @Override
    public void deleteWatchlist(Integer watchlistId, Principal principal) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId)
                .orElseThrow(()-> new RuntimeException("Watchlist not found"));

        if(!watchlist.getUser().getEmail().equals(principal.getName())){
            throw new RuntimeException("You can only delete your own watchlists.");
        }

        watchlistRepository.delete(watchlist);
    }

    @Override
    public List<WatchlistResponse> getUsersWatchlist(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        List<Watchlist> watchlists = watchlistRepository.findByUser(user);

        return watchlists.stream().map(watchlistMapper::toWatchListResponse).collect(Collectors.toList());
    }

}
