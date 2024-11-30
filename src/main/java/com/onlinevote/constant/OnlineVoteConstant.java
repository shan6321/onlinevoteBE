package com.onlinevote.constant;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface OnlineVoteConstant {
    public static final String USER = "Student";
    public static final String OPEN = "Active";
    public static final String CLOSE = "Inactive";
    ZoneId serverZone = ZoneId.of("Asia/Kolkata");
    ZonedDateTime dateTime = ZonedDateTime.now(serverZone);
    LocalDateTime serverDateTime = dateTime.toLocalDateTime();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    String formattedServerDateTime = serverDateTime.format(formatter);


}

