package com.example.Antoflix.dto.response.season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonResponse {
    private int seasonNr;
    private String seriesTitle;
}
