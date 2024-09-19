package com.example.Antoflix.dto.response.movie;

import com.example.Antoflix.dto.response.genre.GenreResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private String title;
    private String description;
    private LocalDate localDate;

    private List<GenreResponse> genres;
}
