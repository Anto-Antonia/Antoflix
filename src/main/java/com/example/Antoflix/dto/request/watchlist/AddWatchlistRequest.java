package com.example.Antoflix.dto.request.watchlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWatchlistRequest {
    private String name;
    private List<Integer> movieId;
}
