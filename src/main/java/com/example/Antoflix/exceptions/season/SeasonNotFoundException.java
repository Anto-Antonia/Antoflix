package com.example.Antoflix.exceptions.season;

public class SeasonNotFoundException extends RuntimeException{
    public SeasonNotFoundException(String message) {
        super(message);
    }
}
