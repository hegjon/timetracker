package com.jonnyware.timetracker;

import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.format.PeriodParser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

public class DurationParser {
    private final String duration;
    private final LocalDate day;

    private static final PeriodFormatter formatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(".")
            .appendMinutes()
            .toFormatter();

    public DurationParser(LocalDate day, String duration) {
        this.duration = duration;
        this.day = day;
    }

    public Collection<Interval> getDuration() {
        Collection<Interval> result = new LinkedList<Interval>();
        for (String interval : duration.split(" ")) {
            result.add(getInterval(interval));
        }
        return Collections.unmodifiableCollection(result);
    }

    private Interval getInterval(String interval) {
        String[] splitted = interval.split("-");
        String from = splitted[0];
        String to = splitted[1];
        return new Interval(time(from), time(to));
    }

    private DateTime time(String value) {
        Period p = formatter.parsePeriod(value);
        LocalTime time = new LocalTime(p.getHours(), p.getMinutes());
        return day.toDateTime(time);
    }
}
