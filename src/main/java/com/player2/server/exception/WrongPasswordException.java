package com.player2.server.exception;

public class WrongPasswordException extends Exception {

    @Override
    public String getMessage() {
        return "Wrong Password!";
    }
}
