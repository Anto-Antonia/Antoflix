package com.example.Antoflix.dto.response.movie;

import com.example.Antoflix.dto.response.genre.GenreResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {
    private String title;
    private String description;
    private String localDate;
    private List<GenreResponse> genres;

//    public MovieResponse(Integer id, String title, String description) {
//    }
}
