package me.niloybiswas.spblog.exception;

public class InvalidTokenException extends Exception{
    private String message;
    public InvalidTokenException(String message) {
        super(message);
        this.message = message;
    }
}
