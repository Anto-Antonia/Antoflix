package com.example.Antoflix.exceptions.movie;

public class AddMovieToWatchlistException extends RuntimeException{
    public AddMovieToWatchlistException(String message) {
        super("Hm, it looks like the movie you wish to add does not exist");
    }
}
