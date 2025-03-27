package com.example.Antoflix.service;

import com.example.Antoflix.dto.response.series.SeriesResponse;

import java.util.List;

public interface SeriesSearchEngineService {
    List<SeriesResponse> searchSeriesByTitleAndKeyword(String title);
}
