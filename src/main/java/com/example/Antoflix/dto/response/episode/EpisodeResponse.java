package com.example.Antoflix.dto.response.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeResponse {
    private String title;
    private int episodeNr;
    private String description;
    private String duration;
    private int seasonNr; // number of season to which the episode belongs to
}
