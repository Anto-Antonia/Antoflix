package com.example.Antoflix.exceptions;

import com.example.Antoflix.exceptions.error.ApiError;
import com.example.Antoflix.exceptions.genre.AddGenreException;
import com.example.Antoflix.exceptions.movie.AddMovieException;
import com.example.Antoflix.exceptions.movie.AddMovieToWatchlistException;
import com.example.Antoflix.exceptions.role.RoleNotFoundException;
import com.example.Antoflix.exceptions.user.UserAlreadyTakenException;
import com.example.Antoflix.exceptions.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionalHandler {
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(AddGenreException.class)
    public ResponseEntity<Object> handleGenreNotFoundException(AddGenreException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserAlreadyTakenException.class)
    public ResponseEntity<Object> handleUserAlreadyTakenException(UserAlreadyTakenException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .debugMessage((ExceptionUtils.getRootCauseMessage(exception)))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AddMovieException.class)
    public ResponseEntity<Object> handleMovieException(AddMovieException exception, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AddMovieToWatchlistException.class)
    public ResponseEntity<Object> handleAddMovieToWatchlistException(AddMovieToWatchlistException exception, HttpServletRequest request){
        ApiError apiError = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(exception.getMessage())
                .debugMessage(ExceptionUtils.getRootCauseMessage(exception))
                .path(request.getRequestURI())
                .build();

        return buildResponseEntity(apiError);
    }

}
