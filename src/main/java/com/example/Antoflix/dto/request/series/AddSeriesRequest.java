package com.example.Antoflix.dto.request.series;

import com.example.Antoflix.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSeriesRequest {
    private String title;
    private String description;
    private String releaseYear;
    private List<Integer>genreId;
}
