package com.ead.authuser.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtil {

    private final static String UTC = "UTC";

    static public LocalDateTime getLocalDateTimeUTC() {
        return LocalDateTime.now(ZoneId.of(UTC));
    }

    static public LocalDate getLocalDateUTC() {
        return LocalDate.now(ZoneId.of(UTC));
    }
}
