package com.seungwoo.books.book;

public enum Target {

    TITLE("title"),
    ISBN("isbn"),
    PUBLISHER("publisher"),
    PERSON("person");

    private final String value;

    Target(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
