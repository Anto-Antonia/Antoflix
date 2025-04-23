package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.episode.AddEpisodeRequest;
import com.example.Antoflix.dto.request.season.AddSeasonRequest;
import com.example.Antoflix.dto.request.series.AddSeriesRequest;
import com.example.Antoflix.dto.request.series.UpdateSeriesRequest;
import com.example.Antoflix.dto.response.episode.EpisodeResponse;
import com.example.Antoflix.dto.response.season.SeasonResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.entity.Episode;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Season;
import com.example.Antoflix.entity.Series;
import com.example.Antoflix.exceptions.episode.EpisodeNotFoundException;
import com.example.Antoflix.exceptions.genre.AddGenreException;
import com.example.Antoflix.exceptions.season.SeasonNotFoundException;
import com.example.Antoflix.exceptions.series.SeriesNotFoundException;
import com.example.Antoflix.mapper.SeasonSeriesEpisodeMapper;
import com.example.Antoflix.repository.EpisodeRepository;
import com.example.Antoflix.repository.GenreRepository;
import com.example.Antoflix.repository.SeasonRepository;
import com.example.Antoflix.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesSeasonEpisodeServiceImpl implements SeriesSeasonEpisodeService {
    private final SeasonSeriesEpisodeMapper mapper;
    private final SeasonRepository seasonRepository;
    private final SeriesRepository seriesRepository;
    private final EpisodeRepository episodeRepository;
    private final GenreRepository genreRepository;

    public SeriesSeasonEpisodeServiceImpl(SeasonSeriesEpisodeMapper mapper, SeasonRepository seasonRepository, SeriesRepository seriesRepository, EpisodeRepository episodeRepository, GenreRepository genreRepository) {
        this.mapper = mapper;
        this.seasonRepository = seasonRepository;
        this.seriesRepository = seriesRepository;
        this.episodeRepository = episodeRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Series addSeries(AddSeriesRequest addSeriesRequest) {
        Series series = mapper.fromAddSeriesRequest(addSeriesRequest);
        return seriesRepository.save(series);
    }

    @Override
    public Season addSeason(AddSeasonRequest addSeasonRequest, Integer seriesId) {
        Season season = mapper.fromAddSeasonRequest(addSeasonRequest, seriesId);
        return seasonRepository.save(season);
    }

    @Override
    public Episode addEpisode(AddEpisodeRequest addEpisodeRequest, Integer seasonId) {
        Episode episode = mapper.fromAddEpisodeRequest(addEpisodeRequest, seasonId);
        return episodeRepository.save(episode);
    }

    @Override
    public SeriesResponse getSeriesById(Integer id) {
        Optional<Series> optionalSeries = seriesRepository.findById(id);

        if(optionalSeries.isPresent()){
            Series series = optionalSeries.get();
            SeriesResponse seriesResponse = mapper.fromSeriesResponse(series);

            return seriesResponse;
        } else{
            throw new SeriesNotFoundException("Series not found.");
        }
    }

    @Override
    public SeasonResponse getSeasonById(Integer id) {
        Optional<Season> optionalSeason = seasonRepository.findById(id);

        if(optionalSeason.isPresent()){
            Season season = optionalSeason.get();
            SeasonResponse response = mapper.fromSeasonResponse(season);

            return response;
        } else{
            throw new SeasonNotFoundException("Season not found.");
        }
    }

    @Override
    public EpisodeResponse getEpisodeById(Integer id) {
        Optional<Episode> optionalEpisode = episodeRepository.findById(id);

        if(optionalEpisode.isPresent()){
            Episode episode = optionalEpisode.get();
            EpisodeResponse episodeResponse = mapper.fromEpisodeResponse(episode);

            return episodeResponse;
        } else{
            throw new EpisodeNotFoundException("Episode not found.");
        }
    }

    @Override
    public void updateSeriesGenre(Integer id, List<Integer> genreId) {
        Series series = seriesRepository.findById(id)                                    // Creating a series object, searching in the repository for it by id,
                .orElseThrow(()-> new SeriesNotFoundException("Series not found"));    // then throwing an exception if not found

        List<Genre> genreList = genreRepository.findAllById(genreId);               // creating a list for genres, searching all of them in the repository by id
        series.setGenres(genreList);                // setting the desired genre to the series

        seriesRepository.save(series);              // saving the series
    }

    @Override
    public void updateSeries(Integer id, UpdateSeriesRequest updateSeriesRequest) {
        Optional<Series> optionalSeries = seriesRepository.findById(id);        // creating an optional of series, searching through repo by id

        if(optionalSeries.isPresent()){                                     // if present, we get the series and update them
            Series series = optionalSeries.get();
            series.setTitle(updateSeriesRequest.getTitle());
            series.setDescription(updateSeriesRequest.getDescription());

            seriesRepository.save(series);
        } else {
            throw new SeriesNotFoundException("The series with id " + id + " does not exist");
        }
    }

    @Override
    public SeriesResponse removeGenreFromSeries(Integer seriesId, Integer genreId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(()-> new SeriesNotFoundException("Series not found."));
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(()-> new AddGenreException("Genre not found."));

        series.getGenres().remove(genre);
        seriesRepository.save(series);
        return mapper.fromSeriesResponse(series);
    }

    @Override
    public void deleteSeries(Integer id) {
        seriesRepository.deleteById(id);
    }

    @Override
    public void deleteSeason(Integer id) {
        seasonRepository.deleteById(id);
    }

    @Override
    public void deleteEpisode(Integer id) {
        episodeRepository.deleteById(id);
    }

    @Override
    public List<SeriesResponse> getAllSeries() {
        List<Series> series = seriesRepository.findAll();
        List<SeriesResponse> seriesResponses = series.stream()
                .map(element -> mapper.fromSeriesResponse(element)).collect(Collectors.toList());


        return seriesResponses;
    }

    @Override
    public List<SeasonResponse> getAllSeasons() {
        List<Season> seasons = seasonRepository.findAll();
        List<SeasonResponse> seasonResponses = seasons.stream()
                .map(element -> mapper.fromSeasonResponse(element)).collect(Collectors.toList());

        return seasonResponses;
    }

    @Override
    public List<EpisodeResponse> getAllEpisodes() {
        List<Episode> episodes = episodeRepository.findAll();
        List<EpisodeResponse> episodeResponses = episodes.stream()
                .map(element -> mapper.fromEpisodeResponse(element)).collect(Collectors.toList());

        return episodeResponses;
    }
}
