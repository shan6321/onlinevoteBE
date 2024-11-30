package com.onlinevote.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DateTimeUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String getFormattedUtcDateTime(ZoneId zoneId) {
        log.info("===Inside getFormattedUtcDateTime Method===");
        ZonedDateTime userCurrentDateTime = ZonedDateTime.now(zoneId);
        log.info("userCurrentDateTime: " + userCurrentDateTime);
        ZonedDateTime utcDateTime = userCurrentDateTime.withZoneSameInstant(ZoneOffset.UTC);
        log.info("utcDateTime: " + utcDateTime);
        log.info("utcDateTime1: " + utcDateTime.format(FORMATTER));
        return utcDateTime.format(FORMATTER);
    }
}
