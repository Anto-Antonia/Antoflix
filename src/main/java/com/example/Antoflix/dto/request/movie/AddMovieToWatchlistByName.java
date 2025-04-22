package com.example.Antoflix.dto.request.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMovieToWatchlistByName {
    String watchlistName;
    Integer movieId;
}
