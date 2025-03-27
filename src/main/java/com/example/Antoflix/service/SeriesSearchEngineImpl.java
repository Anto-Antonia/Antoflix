package com.example.Antoflix.service;

import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.entity.Series;
import com.example.Antoflix.mapper.SeasonSeriesEpisodeMapper;
import com.example.Antoflix.repository.SeriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesSearchEngineImpl implements SeriesSearchEngineService{
    private final SeriesRepository seriesRepository;
    private final SeasonSeriesEpisodeMapper mapper;

    public SeriesSearchEngineImpl(SeriesRepository seriesRepository, SeasonSeriesEpisodeMapper mapper) {
        this.seriesRepository = seriesRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SeriesResponse> searchSeriesByTitleAndKeyword(String title) {
        List<Series> responses = seriesRepository.findSeriesByTitleAndKeyword(title);
        return responses.stream().map(mapper::fromSeriesResponse).collect(Collectors.toList());
    }
}
