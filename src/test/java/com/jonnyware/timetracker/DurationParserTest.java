package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DurationParserTest {
    private final LocalDate day = new LocalDate(2014, 1, 1);

    @Test
    public void hoursAndZeroAppendedMinutes() {
        DurationParser parser = new DurationParser(day, "08.00-16.00");

        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));
        assertEquals(expected, parser.getDuration());
    }

    @Test
    public void hoursAndMinutes() {
        DurationParser parser = new DurationParser(day, "08.10-16.50");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 10), new DateTime(2014, 1, 1, 16, 50));
        assertEquals(expected, parser.getDuration());
    }

    @Test
    public void hoursNoMinutes() {
        DurationParser parser = new DurationParser(day, "08-16");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));
        assertEquals(expected, parser.getDuration());
    }
}
