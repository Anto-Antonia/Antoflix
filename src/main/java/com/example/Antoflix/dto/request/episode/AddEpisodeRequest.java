package com.example.Antoflix.dto.request.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEpisodeRequest {
    private String title;
    private int episodeNr;
    private String description;
    private String duration;
    private Integer seasonId; // number of season to which the episode belongs to
}
