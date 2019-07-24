package com.seungwoo.books.book;

public enum BookSort {

    ACCURACY("accuracy"),
    LATEST("latest"),
    SIM("sim"),
    DATE("date");

    private final String value;

    BookSort(String value) {
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
