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
        Period actual = calculator.calculateDiff(i);
        Period expected = Period.hours(-8).withMinutes(10);

        assertEquals(expected, actual);
    }

    @Test
    public void eighthHoursWorkingOnMonday() {
        Interval i = new Interval(new DateTime(2014, 1, 6, 8, 0), new DateTime(2014, 1, 6, 16, 0));
        Period actual = calculator.calculateDiff(i);

        assertEquals(Period.ZERO, actual);
    }

    @Test
    public void eighthHoursAndTenMinutesWorkingOnFriday() {
        Interval i = new Interval(new DateTime(2014, 1, 3, 8, 0), new DateTime(2014, 1, 3, 16, 10));
        Period actual = calculator.calculateDiff(i);

        assertEquals(Period.minutes(10), actual);
    }

    @Test
    public void twoHoursWorkingOnSaturday() {
        Interval i = new Interval(new DateTime(2014, 1, 4, 8, 0), new DateTime(2014, 1, 4, 10, 0));
        Period actual = calculator.calculateDiff(i);

        assertEquals(Period.hours(2), actual);
    }
}
