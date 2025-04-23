package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.episode.AddEpisodeRequest;
import com.example.Antoflix.dto.request.season.AddSeasonRequest;
import com.example.Antoflix.dto.request.series.AddSeriesRequest;
import com.example.Antoflix.dto.response.episode.EpisodeResponse;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.season.SeasonResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.entity.Episode;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Season;
import com.example.Antoflix.entity.Series;
import com.example.Antoflix.exceptions.season.SeasonNotFoundException;
import com.example.Antoflix.exceptions.series.SeriesNotFoundException;
import com.example.Antoflix.mapper.SeasonSeriesEpisodeMapper;
import com.example.Antoflix.repository.EpisodeRepository;
import com.example.Antoflix.repository.GenreRepository;
import com.example.Antoflix.repository.SeasonRepository;
import com.example.Antoflix.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeriesSeasonEpisodeServiceTest {
    @Mock
    private SeriesRepository seriesRepository;
    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private EpisodeRepository episodeRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private SeriesSeasonEpisodeServiceImpl service;
    @Mock
    private SeasonSeriesEpisodeMapper mapper;
    private Season season;
    private Series series;
    private Episode episode;
    private Genre genre;
    private SeriesResponse seriesResponse;
    private SeasonResponse seasonResponse;
    private EpisodeResponse episodeResponse;
    private GenreResponse genreResponse;

    @BeforeEach
    void setup(){
        genre = new Genre();
        genre.setId(1);
        genre.setGenreName("Action");

        genreResponse = new GenreResponse();
        genreResponse.setName("Action");

        series = new Series();
        series.setId(1);
        series.setTitle("Sample series title");
        series.setDescription("Description");
        series.setReleaseYear("Release date");
        series.setGenres(new ArrayList<>(Arrays.asList(genre)));

        seriesResponse = new SeriesResponse();
        seriesResponse.setTitle("Sample series title");
        seriesResponse.setDescription("Description");
        seriesResponse.setReleaseYear("Release date");
        seriesResponse.setGenres(Arrays.asList(genreResponse));

        season = new Season();
        season.setId(1);
        season.setSeasonNr(1);

        seasonResponse = new SeasonResponse();
        seasonResponse.setSeasonNr(1);
        seasonResponse.setSeriesTitle("Sample series title");

        episode = new Episode();
        episode.setId(1);
        episode.setTitle("Episode title");
        episode.setDescription("Description of the episode");
        episode.setDuration("30min");
        episode.setEpisodeNr(1);
//        episode.setSeason(season);

        episodeResponse = new EpisodeResponse();
        episodeResponse.setEpisodeNr(1);
        episodeResponse.setSeasonNr(1);
        episodeResponse.setDuration("30min");
        episodeResponse.setTitle("Episode title");
        episodeResponse.setDescription("Description of the episode");
    }

    @Test
    public void addSeries_whenSuccessful_returnSeries(){
        AddSeriesRequest request = new AddSeriesRequest("Sample series title", "Description", "Release date", Arrays.asList(genre.getId()));

        // GIVEN
        when(mapper.fromAddSeriesRequest(request)).thenReturn(series);
        when(seriesRepository.save(series)).thenReturn(series);

        // WHEN
        Series savedSeries = service.addSeries(request);

        // THEN
        assertEquals("Sample series title", savedSeries.getTitle());
        assertEquals("Description", savedSeries.getDescription());
        assertEquals("Release date", savedSeries.getReleaseYear());
        assertEquals(1, savedSeries.getGenres().size());
        assertEquals("Action", savedSeries.getGenres().get(0).getGenreName());

        verify(seriesRepository, times(1)).save(series);  // when testing the "add" methods, we verify the object repo then SAVE the object
    }

    @Test
    public void addSeason_whenSuccessful_returnSeason(){
        AddSeasonRequest request = new AddSeasonRequest(1);

        // GIVEN
        when(mapper.fromAddSeasonRequest(request, 1)).thenReturn(season);
        when(seasonRepository.save(season)).thenReturn(season);

        // WHEN
        Season savedSeason = service.addSeason(request, 1);

        // THEN
        assertEquals(1, savedSeason.getSeasonNr());

        verify(seasonRepository, times(1)).save(season);
    }

    @Test
    public void addEpisode_whenSuccessful_returnEpisode(){
        AddEpisodeRequest request = new AddEpisodeRequest("Episode title", 1, "Description of the episode", "30min", 1);

        // GIVEN
        when(mapper.fromAddEpisodeRequest(request, 1)).thenReturn(episode);
        when(episodeRepository.save(episode)).thenReturn(episode);

        // WHEN
        Episode savedEpisode = service.addEpisode(request, 1);

        // THEN
        assertEquals("Episode title", savedEpisode.getTitle());
        assertEquals(1, savedEpisode.getEpisodeNr());
        assertEquals("Description of the episode", savedEpisode.getDescription());
        assertEquals("30min", savedEpisode.getDuration());
//        assertEquals(1, savedEpisode.getSeason());

        verify(episodeRepository, times(1)).save(episode);
    }

    @Test
    public void getSeriesById_whenSeriesExist_thenReturnSeriesResponse(){
        // GIVEN
        when(seriesRepository.findById(1)).thenReturn(Optional.of(series));
        when(mapper.fromSeriesResponse(series)).thenReturn(seriesResponse); // we return the 'entity response' when trying to find it by id not the entity itself

        // WHEN
        SeriesResponse response = service.getSeriesById(1);

        // THEN
        assertNotNull(response);
        assertEquals("Sample series title", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals("Release date", response.getReleaseYear());

        verify(seriesRepository, times(1)).findById(1);  // when testing the "get by" methods, we verify the object repo then FIND IT.
    }

    @Test
    public void getSeriesById_whenSeriesDoesNotExist_throwException(){
        // GIVEN
        when(seriesRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN
        assertThrows(SeriesNotFoundException.class, () -> {                     // using assertThrows for throwing the exception we used in service for
            service.getSeriesById(1);                                           // when we DO NOT find what we need.
        });

        // THEN
        verify(seriesRepository, times(1)).findById(1);
    }

    @Test
    public void getSeasonById_whenSeasonExists_returnSeason(){
        // GIVEN
        when(seasonRepository.findById(1)).thenReturn(Optional.of(season));
        when(mapper.fromSeasonResponse(season)).thenReturn(seasonResponse);

        // WHEN
        SeasonResponse response = service.getSeasonById(1);

        // THEN
        assertNotNull(response);
        assertEquals("Sample series title", response.getSeriesTitle());
        assertEquals(1, response.getSeasonNr());

        verify(seasonRepository, times(1)).findById(1);
    }

    @Test
    public void getSeasonById_whenSeasonDoesNotExist_throwException(){
        // GIVEN
        when(seasonRepository.findById(1)).thenReturn(Optional.empty());

        // WHEN
        assertThrows(SeasonNotFoundException.class, ()->{
            service.getSeasonById(1);
        });

        // THEN
        verify(seasonRepository, times(1)).findById(1);
    }

    @Test
    public void getEpisodeById_whenSuccessful_returnEpisode(){
        // GIVEN
        when(episodeRepository.findById(1)).thenReturn(Optional.of(episode));
        when(mapper.fromEpisodeResponse(episode)).thenReturn(episodeResponse);

        // WHEN
        EpisodeResponse response = service.getEpisodeById(1);

        // THEN
        assertNotNull(response);
        assertEquals();

    }
}
