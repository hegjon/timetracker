package com.jonnyware.timetracker.line;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YearLineParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void missingYearKeyword() {
        new YearLineParser("2014").getYear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingNumber() {
        new YearLineParser("year: ").getYear();
    }

    @Test()
    public void minusYear() {
        int actual = new YearLineParser("year: -2014").getYear();
        assertEquals(-2014, actual);
    }

    @Test
    public void normalYear() {
        int actual = new YearLineParser("year: 2014").getYear();
        assertEquals(2014, actual);
    }

    @Test
    public void normalYear2() {
        int actual = new YearLineParser("Year : 2014").getYear();
        assertEquals(2014, actual);
    }

    @Test
    public void normalYear3() {
        int actual = new YearLineParser("year|2014").getYear();
        assertEquals(2014, actual);
    }
}