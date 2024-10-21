package com.weathermonitoring.exception;

import java.util.List;

import org.springframework.http.HttpStatusCode;

public class ErrorResponse {
    private final HttpStatusCode status;
    private final String error;
    private final String message;
  

    public ErrorResponse(HttpStatusCode httpStatusCode, String error, String message, List<String> errors) {
        this.status = httpStatusCode;
        this.error = error;
        this.message = message;
    }


    // Getters
    public HttpStatusCode getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
