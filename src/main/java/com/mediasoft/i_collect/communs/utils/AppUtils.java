package com.mediasoft.i_collect.communs.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppUtils {

    public static LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date != null ? LocalDate.parse(date, formatter) : null;
    }
}
