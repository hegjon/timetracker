package com.jonnyware.timetracker.line;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeEntryLineParserTest {
    @Test
    public void whiteSpaceOnly() {
        TimeEntryLineParser parser = new TimeEntryLineParser("1 2 =Public Holiday");
        TimeEntry expected = new TimeEntry(1, 2, "=Public Holiday");
        assertEquals(expected, parser.getTimeEntry());
    }

    @Test
    public void slashAndColons() {
        TimeEntryLineParser parser = new TimeEntryLineParser("1/2: =Public Holiday");
        TimeEntry expected = new TimeEntry(1, 2, "=Public Holiday");
        assertEquals(expected, parser.getTimeEntry());
    }

    @Test
    public void zeroAppended() {
        TimeEntryLineParser parser = new TimeEntryLineParser("01.02| +Day off");
        TimeEntry expected = new TimeEntry(1, 2, "+Day off");
        assertEquals(expected, parser.getTimeEntry());
    }
}