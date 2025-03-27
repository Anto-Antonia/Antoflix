package com.example.Antoflix.controller;

import com.example.Antoflix.dto.response.series.SeriesResponse;
import com.example.Antoflix.exceptions.GlobalExceptionalHandler;
import com.example.Antoflix.exceptions.series.SeriesNotFoundException;
import com.example.Antoflix.service.SeriesSearchEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seriesSearch")
public class SeriesSearchEngineController {
    private final SeriesSearchEngineService service;

    public SeriesSearchEngineController(SeriesSearchEngineService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SeriesResponse>> searchSeriesByTitleAndKeyword(@RequestParam String title){
        List<SeriesResponse> responses = service.searchSeriesByTitleAndKeyword(title);
        if(responses.isEmpty()){
           throw new SeriesNotFoundException("Whoops, we don't have the series you're looking for :( ");
        }
        return ResponseEntity.ok(responses);
    }
}
