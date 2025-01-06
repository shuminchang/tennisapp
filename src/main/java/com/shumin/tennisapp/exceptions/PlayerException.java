package com.shumin.tennisapp.exceptions;

public class PlayerException extends Exception {

    public PlayerException(String message) {
        super(message);
    }

    public PlayerException(String message, Throwable exception) {
        super(message, exception);
    }
}
