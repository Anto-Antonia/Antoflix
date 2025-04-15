package com.example.Antoflix.dto.response.watchlist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmptyWatchlistResponse {
    private String name;
    private Integer userId;
}
