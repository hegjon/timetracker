package com.jonnyware.timetracker;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class HourMinutesFormatter {
    private static PeriodFormatter DEFAULT = new PeriodFormatterBuilder()
            .appendHours()
            .appendSuffix("h")
            .appendMinutes()
            .appendSuffix("m")
            .toFormatter();

    public static String print(Period period) {
        return DEFAULT.print(period);
    }

    public static Period parse(String period) {
        return DEFAULT.parsePeriod(period);
    }
}
