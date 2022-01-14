package com.bcavus.invoiceapp.invoiceservice.util;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateOperations {

    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
    public static final String DEFAULT_ZONE_ID = "Europe/Istanbul";

    public static String getDefaultCurrentDateTime(){

        final Clock clock = Clock.system(ZoneId.of(DEFAULT_ZONE_ID));
        final ZonedDateTime zonedDateTime = ZonedDateTime.now(clock);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        return zonedDateTime.format(formatter);
    }
}