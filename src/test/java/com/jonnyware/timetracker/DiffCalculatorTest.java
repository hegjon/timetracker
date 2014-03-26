package com.jonnyware.timetracker;

import org.joda.time.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DiffCalculatorTest {
    private DiffCalculator calculator;

    @Before
    public void createCalculator() {
        calculator = new DiffCalculator(DefaultWeekdayDurationParser.defaultDuration());
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
