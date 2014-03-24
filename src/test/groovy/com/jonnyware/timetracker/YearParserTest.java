package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YearParserTest {
    @Test
    public void oneDay() {
        String content =
                "year: 2014\n" +
                "april:\n" +
                " 1:  08.00-16.00\n";

        YearParser parser = new YearParser(content);
        LocalDate day = new LocalDate(2014, 4, 1);
        DateTime from = day.toDateTime(new LocalTime(8, 0));
        DateTime to = day.toDateTime(new LocalTime(16, 0));
        Interval actual = parser.listTimeEntries().iterator().next();
        assertEquals(new Interval(from, to), actual);
    }

    @Test
    public void holiday() {
        String content =
                "year: 2014\n" +
                "june:\n" +
                " 10:  =Public holiday\n";

        YearParser parser = new YearParser(content);
        LinkedHashMap<LocalDate, NeutralDay> map = new LinkedHashMap<LocalDate, NeutralDay>(1);
        map.put(new LocalDate(2014, 6, 10), new NeutralDay("Public holiday"));
        assertEquals(parser.getEntries(), map);
    }

    @Test
    public void vacation() {
        String content =
                "year: 2014\n" +
                "february:\n" +
                " 27:  +Skiing\n";

        YearParser parser = new YearParser(content);
        LinkedHashMap<LocalDate, Vacation> map = new LinkedHashMap<LocalDate, Vacation>(1);
        map.put(new LocalDate(2014, 2, 27), new Vacation("Skiing"));
        assertEquals(parser.getEntries(), map);
    }

    @Test
    public void empty() {
        String content = "year: 2014";

        YearParser parser = new YearParser(content);
        assertEquals(2014, parser.getYear());
        assertTrue(parser.listTimeEntries().isEmpty());
    }
}
