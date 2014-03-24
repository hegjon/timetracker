package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DurationParserTest {
    private final LocalDate day = new LocalDate(2014, 1, 1);

    @Test
    public void onlyHours() {
        DurationParser parser = new DurationParser(day, "08.00-16.00");

        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));
        assertEquals(expected, parser.getDuration());
    }

    @Test
    public void hoursPlus10Minutes() {
        DurationParser parser = new DurationParser(day, "08.00-16.10");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 10));
        assertEquals(expected, parser.getDuration());
    }

    @Test
    public void hoursMinus10Minutes() {
        DurationParser parser = new DurationParser(day, "08.00-15.50");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 15, 50));
        assertEquals(expected, parser.getDuration());
    }

    @Test
    public void hoursMinus10Minutes2() {
        DurationParser parser = new DurationParser(day, "08.50-16.00");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 50), new DateTime(2014, 1, 1, 16, 00));
        assertEquals(expected, parser.getDuration());
    }
}
