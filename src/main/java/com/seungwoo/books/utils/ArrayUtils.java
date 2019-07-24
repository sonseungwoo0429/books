package com.seungwoo.books.utils;

import java.util.Collection;

import static java.util.stream.Collectors.joining;

public abstract class ArrayUtils {

    private ArrayUtils() {
        throw new UnsupportedOperationException();
    }

    public static String commaSeparatedListToString(Collection<String> list) {
        return list.stream().collect(joining(", "));
    }
}
