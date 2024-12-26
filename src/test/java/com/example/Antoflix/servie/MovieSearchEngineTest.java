package com.example.Antoflix.servie;

import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.service.MovieSearchEngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieSearchEngineTest {
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private MovieSearchEngineImpl movieSearchEngineImpl;
    @Mock
    private MovieGenreMapper movieGenreMapper;
    private Movie movie;
    private MovieResponse movieResponse;
    private Genre genre;

    @BeforeEach
    void setup(){
        genre = new Genre();
        genre.setId(1);
        genre.setGenreName("action");

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Sample movie");
        movie.setGenres(List.of(genre));

        movieResponse = new MovieResponse();
        movieResponse.setTitle("Sample movie");
    }

    @Test
    public void searchMovieByKeyword_whenMovieExists_thenReturnMovieResponse(){
        String keyword = "sample";

        //GIVEN
        when(movieRepository.findByTitleContainingKeyword(keyword)).thenReturn(List.of(movie));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        //WHEN
        List<MovieResponse> responses = movieSearchEngineImpl.searchMovieByKeyword(keyword);

        //THEN
        assertEquals(1, responses.size());
        assertEquals("Sample movie", responses.get(0).getTitle());

        verify(movieRepository, times(1)).findByTitleContainingKeyword(keyword);
        verify(movieGenreMapper, times(1)).fromMovieResponse(movie);

    }

    @Test
    public void searchMovieByTitle_whenMovieExists_thenReturnMovieResponse(){
        String title= "movie";

        when(movieRepository.findByTitleIgnoreCase(title)).thenReturn(List.of(movie));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        List<MovieResponse> responses = movieSearchEngineImpl.searchMovieByTitle(title);

        assertEquals(1, responses.size());
        assertEquals("Sample movie", responses.get(0).getTitle());

        verify(movieRepository, times(1)).findByTitleIgnoreCase(title);
        verify(movieGenreMapper, times(1)).fromMovieResponse(movie);
    }

    @Test
    public void searchMovieByGenre_whenMovieExists_thenReturnMovieResponse(){
        String genre = "action";

        when(movieRepository.findByGenre(genre)).thenReturn(List.of(movie));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        List<MovieResponse> responses = movieSearchEngineImpl.searchMovieByGenre(genre);

        assertEquals(1, responses.size());
        assertEquals("Sample movie", responses.get(0).getTitle());

        verify(movieRepository, times(1)).findByGenre(genre);
        verify(movieGenreMapper, times(1)).fromMovieResponse(movie);
    }

}
