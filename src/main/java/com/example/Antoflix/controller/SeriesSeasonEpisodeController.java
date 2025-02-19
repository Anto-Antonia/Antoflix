package com.example.Antoflix.controller;

import com.example.Antoflix.dto.request.episode.AddEpisodeRequest;
import com.example.Antoflix.dto.request.season.AddSeasonRequest;
import com.example.Antoflix.dto.request.series.AddSeriesRequest;
import com.example.Antoflix.dto.request.series.UpdateSeriesRequest;
import com.example.Antoflix.dto.response.episode.EpisodeResponse;
import com.example.Antoflix.dto.response.season.SeasonResponse;
import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.service.SeriesSeasonEpisodeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/series")
public class SeriesSeasonEpisodeController {
    private final SeriesSeasonEpisodeService service;

    public SeriesSeasonEpisodeController(SeriesSeasonEpisodeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addSeries(@Valid @RequestBody AddSeriesRequest addSeriesRequest){
        service.addSeries(addSeriesRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{seriesId}/season")
    public ResponseEntity<Void> addSeason(@PathVariable Integer seriesId, @Valid @RequestBody AddSeasonRequest addSeasonRequest){
        service.addSeason(addSeasonRequest, seriesId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/episodes/{seasonId}")
    public ResponseEntity<Void> addEpisode(@PathVariable Integer seasonId, @RequestBody @Valid AddEpisodeRequest addEpisodeRequest){
        service.addEpisode(addEpisodeRequest, seasonId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponse> getSeriesById(@PathVariable Integer id){
        SeriesResponse response = service.getSeriesById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/season/{id}")
    public ResponseEntity<SeasonResponse> getSeasonById(@PathVariable Integer id){
        SeasonResponse response = service.getSeasonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/episode/{id}")
    public ResponseEntity<EpisodeResponse> getEpisodeById(@PathVariable Integer id){
        EpisodeResponse response = service.getEpisodeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("genre/{id}")
    public ResponseEntity<String> updateSeriesGenre(@PathVariable Integer id, @RequestBody List<Integer> genreId){
        service.updateSeriesGenre(id, genreId);
        return ResponseEntity.ok("Genres updates successfully to series with id: " + id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSeries(@PathVariable Integer id, @RequestBody UpdateSeriesRequest updateSeriesRequest){
        service.updateSeries(id, updateSeriesRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{seriesId}/{genreId}")
    public ResponseEntity<SeriesResponse> removeGenreFromSeries(@PathVariable Integer seriesId, @PathVariable Integer genreId){
        SeriesResponse response = service.removeGenreFromSeries(seriesId, genreId);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeries(@PathVariable Integer id){
        service.deleteSeries(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/season/{id}")
    public ResponseEntity<Void> deleteSeason(@PathVariable Integer id){
        service.deleteSeason(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/episode/{id}")
    public ResponseEntity<Void> deleteEpisode(@PathVariable Integer id){
        service.deleteEpisode(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<List<SeriesResponse>> getAllSeries(){
        List<SeriesResponse> responses = service.getAllSeries();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/seasons")
    public ResponseEntity<List<SeasonResponse>> getAllSeasons(){
        List<SeasonResponse> responses = service.getAllSeasons();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/episodes")
    public ResponseEntity<List<EpisodeResponse>> getAllEpisodes(){
        List<EpisodeResponse> responses = service.getAllEpisodes();
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
}
