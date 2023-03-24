package com.netblog.netblog.exceptions;

public class PasswordFieldEmptyException extends RuntimeException {
    public PasswordFieldEmptyException(String message) {
        super(message);
    }
}
