package com.example.Antoflix.dto.request.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieRequest {
    private String title;
    private String description;
    private String releaseDate;
    private List<Integer> genreId;
}
