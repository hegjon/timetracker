package com.jonnyware.timetracker;

import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Map;

public class DiffCalculator {
    private final Map<Integer, Period> defaultWeekdayDuration;

    public DiffCalculator(Map<Integer, Period> defaultWeekdayDuration) {
        this.defaultWeekdayDuration = defaultWeekdayDuration;
    }

    public Period calculateDiff(Interval interval) {
        int dayOfWeek = interval.getStart().getDayOfWeek();
        Period defaultDuration = defaultWeekdayDuration.get(dayOfWeek);
        return interval.toPeriod().minus(defaultDuration);
    }
}
