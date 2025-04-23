package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.watchlist.AddWatchlistRequest;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.dto.response.watchlist.WatchlistResponse;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.entity.User;
import com.example.Antoflix.entity.Watchlist;
import com.example.Antoflix.mapper.WatchlistMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.repository.UserRepository;
import com.example.Antoflix.repository.WatchlistRepository;
import com.example.Antoflix.service.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {

    @Mock
    private WatchlistRepository watchlistRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WatchlistMapper watchlistMapper;
    @InjectMocks
    private WatchlistServiceImpl watchlistService;
    private Watchlist watchlist;
    private Movie movie;
    private User user;
    private WatchlistResponse watchlistResponse;

    @BeforeEach
    void setup(){
        User user = new User();
        user.setId(1);

        Movie movie = new Movie();
        movie.setId(1);

        Watchlist watchList = new Watchlist();
        watchList.setId(1);
        watchList.setName("New watchlist");
        watchList.setUser(user);
    }

    @Test
    public void createWatchlist_whenSuccessful_createWatchlist(){

        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUser()).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        List<Integer> movieIds = List.of(1);

        AddWatchlistRequest addWatchlistRequest = new AddWatchlistRequest();
        addWatchlistRequest.setName("New watchlist");
        addWatchlistRequest.setMovieId(movieIds);

        Movie movie = new Movie();
        movie.setId(1);

        Watchlist expectedWatchlist = new Watchlist();
        expectedWatchlist.setId(1);
        expectedWatchlist.setMovies(List.of(movie));
        expectedWatchlist.setName("New watchlist");

        when(movieRepository.findAllById(movieIds)).thenReturn(List.of(movie));
        when(watchlistMapper.createWatchlistRequest(addWatchlistRequest, Arrays.asList(movie), user)).thenReturn(expectedWatchlist);
        when(watchlistRepository.save(expectedWatchlist)).thenReturn(expectedWatchlist);

        Watchlist actualWatchlist = watchlistService.createWatchlist(addWatchlistRequest);

        assertEquals(expectedWatchlist, actualWatchlist);
        verify(movieRepository, times(1)).findAllById(movieIds);
        verify(watchlistMapper, times(1)).createWatchlistRequest(addWatchlistRequest, Arrays.asList(movie), user);
        verify(watchlistRepository, times(1)).save(expectedWatchlist);

    }

    @Test
    public void addMovieToWatchlist_whenSuccessful_addMovie(){
        int movieId = 1;
        int watchlistId = 1;

        Watchlist watchlist = new Watchlist();
        watchlist.setId(watchlistId);
        watchlist.setMovies(new ArrayList<>());

        Movie movie = new Movie();
        movie.setId(movieId);

        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(watchlist));
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        watchlistService.addMovieToWatchList(watchlistId, movieId);

        assertTrue(watchlist.getMovies().contains(movie));
        verify(watchlistRepository, times(1)).findById(watchlistId);
        verify(movieRepository, times(1)).findById(movieId);
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    @Test
    public void removeMovieFromWatchlist_whenSuccessful_removeMovie(){
        int movieId = 1;
        int watchlistId = 1;

        Movie movie = new Movie();
        movie.setId(movieId);

        Watchlist watchlist = new Watchlist();
        watchlist.setId(watchlistId);
        watchlist.setMovies(new ArrayList<>(List.of(movie)));

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(watchlist));

        watchlistService.removeMovieFromWatchlist(watchlistId, movieId);

        assertFalse(watchlist.getMovies().contains(movie));
        verify(watchlistRepository, times(1)).findById(watchlistId);
        verify(movieRepository, times(1)).findById(movieId);
        verify(watchlistRepository, times(1)).save(watchlist);
    }

    @Test
    public void getWatchlistById_whenSuccessful_returnWatchlist(){
        int watchlistId = 1;

        Movie movie = new Movie();
        movie.setId(1);

        Watchlist watchlist = new Watchlist();
        watchlist.setMovies(List.of(movie));
        watchlist.setId(1);
        watchlist.setName("New watchlist");

        WatchlistResponse watchlistResponse = new WatchlistResponse();
        watchlistResponse.setName("New watchlist");
        watchlistResponse.setMovies(List.of(new MovieResponse()));

        when(watchlistRepository.findById(watchlistId)).thenReturn(Optional.of(watchlist));
        when(watchlistMapper.toWatchListResponse(watchlist)).thenReturn(watchlistResponse);

        WatchlistResponse response = watchlistService.getWatchlistById(watchlistId);

        assertEquals(watchlistResponse, response);
        verify(watchlistRepository, times(1)).findById(watchlistId);
        verify(watchlistMapper, times(1)).toWatchListResponse(watchlist);
    }
}
