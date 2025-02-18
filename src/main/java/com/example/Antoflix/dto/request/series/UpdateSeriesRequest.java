package com.example.Antoflix.dto.request.series;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSeriesRequest {
    private String title;
    private String description;
}
