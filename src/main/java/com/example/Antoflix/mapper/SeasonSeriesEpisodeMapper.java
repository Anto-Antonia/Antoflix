package com.example.Antoflix.mapper;

import com.example.Antoflix.dto.request.episode.AddEpisodeRequest;
import com.example.Antoflix.dto.request.season.AddSeasonRequest;
import com.example.Antoflix.dto.request.series.AddSeriesRequest;
import com.example.Antoflix.dto.response.genre.GenreResponse;
import com.example.Antoflix.dto.response.season.SeasonResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.entity.Episode;
import com.example.Antoflix.entity.Genre;
import com.example.Antoflix.entity.Season;
import com.example.Antoflix.entity.Series;
import com.example.Antoflix.repository.GenreRepository;
import com.example.Antoflix.repository.SeasonRepository;
import com.example.Antoflix.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeasonSeriesEpisodeMapper {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private SeriesRepository seriesRepository;
    @Autowired
    private SeasonRepository seasonRepository;

    public Series fromAddSeriesRequest(AddSeriesRequest addSeriesRequest){
        Series series = new Series();
        series.setTitle(addSeriesRequest.getTitle());
        series.setDescription(addSeriesRequest.getDescription());
        series.setReleaseYear(addSeriesRequest.getReleaseYear());

        List<Genre>genres = genreRepository.findAllById(addSeriesRequest.getGenreId());
        series.setGenres(genres);

        return series;
    }

    public SeriesResponse fromSeriesResponse(Series series){
        SeriesResponse seriesResponse = new SeriesResponse();
        seriesResponse.setTitle(series.getTitle());
        seriesResponse.setDescription(series.getDescription());
        seriesResponse.setReleaseYear(series.getReleaseYear());

        List<GenreResponse> genreResponses = series.getGenres().stream()
                .map(this::fromGenreResponse)
                .collect(Collectors.toList());
        seriesResponse.setGenres(genreResponses);

        return seriesResponse;
    }

    public Season fromAddSeasonRequest(AddSeasonRequest addSeasonRequest, Integer seriesId){
        Series series = seriesRepository.findById(seriesId).orElseThrow(()-> new RuntimeException("Series not found"));

        Season season = new Season();
        season.setSeasonNr(addSeasonRequest.getSeasonNr());
        season.setSeries(series);

        return season;
    }

    public SeasonResponse fromSeasonResponse(Season season){
        SeasonResponse seasonResponse = new SeasonResponse();
        seasonResponse.setSeasonNr(season.getSeasonNr());
        seasonResponse.setSeriesTitle(season.getSeries().getTitle());

        return seasonResponse;
    }

    public Episode fromAddEpisodeRequest(AddEpisodeRequest addEpisodeRequest, Integer seasonId){
        Season season = seasonRepository.findById(seasonId).orElseThrow(()->new RuntimeException("Season not found"));

        Episode episode = new Episode();
        episode.setTitle(addEpisodeRequest.getTitle());

    }

    public GenreResponse fromGenreResponse(Genre genre){
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setName(genre.getGenreName());

        return genreResponse;
    }
}
