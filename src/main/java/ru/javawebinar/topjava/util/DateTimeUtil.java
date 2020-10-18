package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return (startTime == null || lt.compareTo(startTime) >= 0) && (endTime == null || lt.compareTo(endTime) < 0);
    }

    public static boolean isBetweenClosed(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return (startDate == null || date.compareTo(startDate) >= 0) && (endDate == null || date.compareTo(endDate) <= 0);
    }

    public static LocalDate parseLocalDate(String date) {
        if (StringUtils.isEmpty(date))
            return null;
        return LocalDate.parse(date);
    }

    public static LocalTime parseLocalTime(String time) {
        if (StringUtils.isEmpty(time))
            return null;
        return LocalTime.parse(time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

}

