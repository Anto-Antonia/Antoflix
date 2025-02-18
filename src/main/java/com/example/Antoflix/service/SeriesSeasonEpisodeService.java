package com.example.Antoflix.service;

import com.example.Antoflix.dto.request.episode.AddEpisodeRequest;
import com.example.Antoflix.dto.request.season.AddSeasonRequest;
import com.example.Antoflix.dto.request.series.AddSeriesRequest;
import com.example.Antoflix.dto.request.series.UpdateSeriesRequest;
import com.example.Antoflix.dto.response.episode.EpisodeResponse;
import com.example.Antoflix.dto.response.season.SeasonResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.entity.Episode;
import com.example.Antoflix.entity.Season;
import com.example.Antoflix.entity.Series;

import java.util.List;

public interface SeriesSeasonEpisodeService {
    Series addSeries(AddSeriesRequest addSeriesRequest);
    Season addSeason(AddSeasonRequest addSeasonRequest, Integer seriesId);
    Episode addEpisode(AddEpisodeRequest addEpisodeRequest, Integer seasonId);
    SeriesResponse getSeriesById(Integer id);
    SeasonResponse getSeasonById(Integer id);
    EpisodeResponse getEpisodeById(Integer id);
    void updateSeriesGenre(Integer id, List<Integer> genreId);
    void updateSeries(Integer id, UpdateSeriesRequest updateSeriesRequest);
    SeriesResponse removeGenreFromSeries(Integer seriesId, Integer genreId);
    void deleteSeries(Integer id);
    void deleteSeason(Integer id);
    void deleteEpisode(Integer id);
    List<SeriesResponse> getAllSeries();
    List<SeasonResponse> getAllSeasons();
    List<EpisodeResponse> getAllEpisodes();
}
