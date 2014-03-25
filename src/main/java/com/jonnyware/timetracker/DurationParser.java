package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class DurationParser {
    private final String duration;
    private final LocalDate day;

    public DurationParser(LocalDate day, String duration) {
        this.duration = duration;
        this.day = day;
    }

    public Interval getDuration() {
        String[] splitted = duration.split("-");
        String from = splitted[0];
        String to = splitted[1];
        return new Interval(time(from), time(to));
    }

    private DateTime time(String value) {
        String[] values = value.split("\\.");
        Integer hour = Integer.valueOf(values[0]);
        Integer minute = Integer.valueOf(values[1]);

        LocalTime time = new LocalTime(hour, minute);
        return day.toDateTime(time);
    }
}
