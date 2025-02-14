package com.example.Antoflix.dto.response.series;

import com.example.Antoflix.dto.response.genre.GenreResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesResponse {
    private String title;
    private String description;
    private String releaseYear;
    private List<GenreResponse> genres;
}
