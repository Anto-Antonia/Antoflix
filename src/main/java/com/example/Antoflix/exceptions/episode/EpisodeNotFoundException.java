package com.example.Antoflix.exceptions.episode;

public class EpisodeNotFoundException extends RuntimeException{
    public EpisodeNotFoundException(String message) {
        super(message);
    }
}
