package com.mangareader.common.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(int status, String message) {
}
