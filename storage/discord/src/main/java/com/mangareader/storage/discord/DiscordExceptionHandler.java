package com.mangareader.storage.discord;

import com.mangareader.common.exception.ExceptionResponse;
import com.mangareader.storage.discord.exception.DiscordFileCorruptedException;
import com.mangareader.storage.discord.exception.DiscordFileNotFoundException;
import com.mangareader.storage.discord.exception.NotADiscordFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DiscordExceptionHandler {

    @ExceptionHandler(DiscordFileNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDiscordFileNotFoundException(DiscordFileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(DiscordFileCorruptedException.class)
    public ResponseEntity<ExceptionResponse> handleDiscordFileCorruptedException(DiscordFileCorruptedException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(NotADiscordFileException.class)
    public ResponseEntity<ExceptionResponse> handleNotADiscordFileException(NotADiscordFileException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build());
    }

}
