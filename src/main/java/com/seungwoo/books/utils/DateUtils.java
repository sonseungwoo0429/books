package com.seungwoo.books.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    public static LocalDate dateFormatter(String str) {
        try {
            return LocalDate.parse(str, DATE_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }
}
