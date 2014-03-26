package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.Map;

public class DiffCalculator {
    private final Map<Integer, Duration> defaultWeekdayDuration;

    public DiffCalculator(Map<Integer, Duration> defaultWeekdayDuration) {
        this.defaultWeekdayDuration = defaultWeekdayDuration;
    }

    public Duration calculateDiff(Interval interval) {
        int dayOfWeek = interval.getStart().getDayOfWeek();
        Duration defaultDuration = defaultWeekdayDuration.get(dayOfWeek);
        return interval.toDuration().minus(defaultDuration);
    }
}
