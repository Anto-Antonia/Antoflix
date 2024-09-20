package com.example.Antoflix.dto.request.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieToWatchlist {
    private Integer watchlistId;
    private Integer movieId;
}
