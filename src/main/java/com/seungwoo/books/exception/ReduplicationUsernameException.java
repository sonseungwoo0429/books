package com.seungwoo.books.exception;

public class ReduplicationUsernameException extends RuntimeException {

    private final String username;

    public ReduplicationUsernameException(String username) {
        this.username = username;
    }

}
