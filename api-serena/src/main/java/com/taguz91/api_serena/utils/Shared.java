package com.taguz91.api_serena.utils;

import java.time.LocalDateTime;

public class Shared {

    public static LocalDateTime startDate(String date) {
        return LocalDateTime.parse(parseDate(date) + "T00:00:00");
    }

    public static LocalDateTime endDate(String date) {
        return LocalDateTime.parse(parseDate(date) + "T23:59:59");
    }

    public static String parseDate(String date) {
        String[] parts = date.split("-");
        String parsedDate = "";
        int year = Integer.parseInt(parts[0]);

        parsedDate += year + "-";

        int month = Integer.parseInt(parts[1]);

        if (month < 10) {
            parsedDate += "0" + month + "-";
        } else {
            parsedDate += month + "-";
        }

        int day = Integer.parseInt(parts[2]);

        if (day < 10) {
            parsedDate += "0" + day;
        } else {
            parsedDate += day;
        }

        return parsedDate;
    }
}
