package com.example.SongFinder.Exceptions;

public class UnauthorizedRequestException extends Exception{
    public UnauthorizedRequestException() {
    }

    public UnauthorizedRequestException(String message) {
        super(message);
    }
}
