package com.jonnyware.timetracker;

import org.joda.time.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class DiffCalculatorTest {

    private DiffCalculator calculator;

    @Before
    public void createCalculator() {
        Map<Integer, Duration> defaultWeekdayDuration = new TreeMap<Integer, Duration>();
        defaultWeekdayDuration.put(1, Duration.standardHours(8));
        defaultWeekdayDuration.put(2, Duration.standardHours(8));
        defaultWeekdayDuration.put(3, Duration.standardHours(8));
        defaultWeekdayDuration.put(4, Duration.standardHours(8));
        defaultWeekdayDuration.put(5, Duration.standardHours(8));
        defaultWeekdayDuration.put(6, Duration.ZERO);
        defaultWeekdayDuration.put(7, Duration.ZERO);

        calculator = new DiffCalculator(defaultWeekdayDuration);
    }

    @Test
    public void tenMinutesWorkingOnWednesday() {
        Interval i = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 8, 10));
        Duration actual = calculator.calculateDiff(i);
        Duration expected = new Period(-7, -50, 0, 0).toStandardDuration();

        assertEquals(expected, actual);
    }

    @Test
    public void eighthHoursWorkingOnMonday() {
        Interval i = new Interval(new DateTime(2014, 1, 6, 8, 0), new DateTime(2014, 1, 6, 16, 0));
        Duration actual = calculator.calculateDiff(i);

        assertEquals(Duration.ZERO, actual);
    }

    @Test
    public void eighthHoursAndTenMinutesWorkingOnFriday() {
        Interval i = new Interval(new DateTime(2014, 1, 3, 8, 0), new DateTime(2014, 1, 3, 16, 10));
        Duration actual = calculator.calculateDiff(i);

        assertEquals(Duration.standardMinutes(10), actual);
    }

    @Test
    public void twoHoursWorkingOnSaturday() {
        Interval i = new Interval(new DateTime(2014, 1, 4, 8, 0), new DateTime(2014, 1, 4, 10, 0));
        Duration actual = calculator.calculateDiff(i);

        assertEquals(Duration.standardHours(2), actual);
    }
}
