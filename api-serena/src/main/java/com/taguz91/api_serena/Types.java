package com.taguz91.api_serena;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Types {
    // Date format
    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    public static String getNowDate() {
        LocalDateTime date = LocalDateTime.now();
        return date.format(DATE_FORMAT);
    }
}
