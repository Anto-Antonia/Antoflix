package com.example.Antoflix.exceptions.series;

public class SeriesNotFoundException extends RuntimeException{
    public SeriesNotFoundException(String message) {
        super(message);
    }
}
