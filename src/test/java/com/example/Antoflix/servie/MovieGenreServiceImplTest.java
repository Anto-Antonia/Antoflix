package com.example.Antoflix.servie;

import com.example.Antoflix.dto.request.genre.AddGenreRequest;
import com.example.Antoflix.dto.request.movie.AddMovieRequest;
import com.example.Antoflix.dto.request.movie.UpdateMovieRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.movie.MovieResponse;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Movie;
import com.example.Antoflix.exceptions.movie.AddMovieException;
import com.example.Antoflix.mapper.MovieGenreMapper;
import com.example.Antoflix.repository.GenreRepository;
import com.example.Antoflix.repository.MovieRepository;
import com.example.Antoflix.service.MovieGenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieGenreServiceImplTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private MovieGenreServiceImpl movieGenreService;
    @Mock
    private MovieGenreMapper movieGenreMapper;
    private Movie movie;
    private Genre genre;
    private MovieResponse movieResponse;
    private GenreResponse genreResponse;

    @BeforeEach
    void setup(){
        genre = new Genre();
        genre.setId(1);
        genre.setGenreName("action");

        movie = new Movie();
        movie.setId(1);
        movie.setTitle("Sample movie");
        movie.setDescription("Description");
        movie.setReleaseDate("Release date");
        movie.setGenres(new ArrayList<>(Arrays.asList(genre))); /*changed from List.of() to Arrays.asList, this way I create a mutable list
                                                                that can be modified during the test */
        genre.setMovies(new ArrayList<>(Arrays.asList(movie))); // add movie to genre's movie list

        genreResponse = new GenreResponse();
        genreResponse.setName("Action");

        movieResponse = new MovieResponse();
        movieResponse.setTitle("Sample movie");
        movieResponse.setDescription("Description");
        movieResponse.setLocalDate("Release date");
        movieResponse.setGenres(new ArrayList<>(Arrays.asList(genreResponse)));
    }

    @Test
    public void addMovie_whenSuccessful_returnMovie(){
       AddMovieRequest addMovieRequest = new AddMovieRequest();
       addMovieRequest.setTitle("Sample movie");
       addMovieRequest.setDescription("Description");
       addMovieRequest.setReleaseDate("Release date");
       addMovieRequest.setGenreId(List.of(genre.getId()));

       when(movieGenreMapper.fromAddMovieRequest(addMovieRequest)).thenReturn(movie);
       when(movieRepository.save(movie)).thenReturn(movie);

        Movie savedMovie = movieGenreService.addMovie(addMovieRequest);

        assertEquals("Sample movie", savedMovie.getTitle());
        assertEquals("Description", savedMovie.getDescription());
        assertEquals("Release date", savedMovie.getReleaseDate());
        assertEquals(1, savedMovie.getGenres().size());
        assertEquals("action", savedMovie.getGenres().get(0).getGenreName());

        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void addGenre_whenSuccessful_returnGenre(){
        AddGenreRequest genreRequest = new AddGenreRequest();
        genreRequest.setName("action");

        when(movieGenreMapper.fromAddGenreRequest(genreRequest)).thenReturn(genre);
        when(genreRepository.save(genre)).thenReturn(genre);

        Genre savedGenre = movieGenreService.addGenre(genreRequest);

        assertEquals("action", savedGenre.getGenreName());

        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    public void getMovieById_whenMovieExists_thenReturnMovieResponse(){
        int movieId = 1;

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        MovieResponse movieResponse = movieGenreService.getMovieById(movieId);

        assertEquals("Sample movie", movieResponse.getTitle());

        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void getMovieById_whenMovieDoesNotExists_thenThrowAddMovieException(){
        int movieId = 1;

        when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        assertThrows(AddMovieException.class, () -> {
            movieGenreService.getMovieById(movieId);
        });

        verify(movieRepository, times(1)).findById(movieId);
    }

    @Test
    public void updateMovieGenres_whenSuccessful_updateGenres(){
        int movieId = 1;

        List<Integer> genreId = List.of(1);
        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(genreRepository.findAllById(genreId)).thenReturn(List.of(genre));

        movieGenreService.updateMovieGenres(movieId, genreId);

        assertEquals(1, movie.getGenres().size());

        verify(movieRepository, times(1)).findById(movieId);
        verify(genreRepository, times(1)).findAllById(genreId);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void removeGenreFromMovie_whenSuccessful_thenReturnMovieResponse(){
        int movieId = 1;
        int genreId = 1;

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        MovieResponse updatedMovieResponse = movieGenreService.removeGenreFromMovie(movieId, genreId);

        assertEquals(0, movie.getGenres().size());

        verify(movieRepository, times(1)).findById(movieId);
        verify(genreRepository, times(1)).findById(genreId);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void updateMovie_whenSuccessful_updateMovie(){
        int movieId = 1;
        UpdateMovieRequest updateMovieRequest = new UpdateMovieRequest();
        updateMovieRequest.setTitle("New movie title");
        updateMovieRequest.setDescription("New description if needed");

        when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieGenreService.updateMovie(movieId, updateMovieRequest);

        assertEquals("New movie title", movie.getTitle());
        assertEquals("New description if needed", movie.getDescription());

        verify(movieRepository, times(1)).findById(movieId);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void deleteMovie_whenSuccessful_deleteMovie(){
        int movieId = 1;

        when(movieRepository.existsById(movieId)).thenReturn(true);

        movieGenreService.deleteMovie(movieId);

        verify(movieRepository, times(1)).existsById(movieId); // verifies if the movie exists
        verify(movieRepository, times(1)).deleteById(movieId); // then deletes the movie
    }

    @Test
    public void deleteGenre_whenSuccessful_deleteGenreAndUpdateMovies(){
        int genreId = 1;

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));

        movieGenreService.deleteGenre(genreId);

        assertEquals(0, movie.getGenres().size());

        verify(genreRepository, times(1)).findById(genreId);
        verify(genreRepository, times(1)).deleteById(genreId);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    public void getAllMovie_whenCalled_returnListOfMovie(){
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(movieGenreMapper.fromMovieResponse(movie)).thenReturn(movieResponse);

        List<MovieResponse> movieResponses = movieGenreService.getAllMovies();

        assertEquals(1, movieResponses.size());

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void getAllGenres_whenCalled_returnListOfGenres(){
        when(genreRepository.findAll()).thenReturn(List.of(genre));
        when(movieGenreMapper.fromGenreResponse(genre)).thenReturn(genreResponse);

        List<GenreResponse> genreResponses = movieGenreService.getAllGenres();

        assertEquals(1, genreResponses.size());

        verify(genreRepository, times(1)).findAll();
    }

}
