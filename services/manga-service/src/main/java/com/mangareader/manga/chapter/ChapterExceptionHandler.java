package com.mangareader.manga.chapter;

import com.mangareader.common.exception.ExceptionResponse;
import com.mangareader.manga.chapter.exception.ChapterAlreadyExistsException;
import com.mangareader.manga.chapter.exception.ChapterCorruptedException;
import com.mangareader.manga.chapter.exception.ChapterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ChapterExceptionHandler {

    @ExceptionHandler(ChapterAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleChapterAlreadyExistsException(ChapterAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ChapterCorruptedException.class)
    public ResponseEntity<ExceptionResponse> handleChapterCorruptedException(ChapterCorruptedException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(ChapterNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleChapterNotFoundException(ChapterNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .message(exception.getMessage())
                        .build());
    }

}
