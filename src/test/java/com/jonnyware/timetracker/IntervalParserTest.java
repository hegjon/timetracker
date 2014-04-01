/*
 *  Copyright 2014 Jonny Heggheim
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntervalParserTest {
    private final LocalDate day = new LocalDate(2014, 1, 1);

    @Test
    public void hoursAndZeroAppendedMinutes() {
        IntervalParser parser = new IntervalParser(day, "08.00-16.00");
        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(8));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void hoursAndMinutes() {
        IntervalParser parser = new IntervalParser(day, "08.10-16.50");
        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(8).withMinutes(40));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void hoursNoMinutes() {
        IntervalParser parser = new IntervalParser(day, "08-16");
        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(8));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void multipleEntries() {
        IntervalParser parser = new IntervalParser(day, "08.00-16 + 18-20.10");

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(10).withMinutes(10));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void openInterval() {
        DateTime now = new DateTime(2014, 1, 1, 13, 43);
        IntervalParser parser = new IntervalParser(day, "9.31-", now);

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(4).withMinutes(12));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void openClosedAndOneOpenInterval() {
        DateTime now = new DateTime(2014, 1, 1, 21, 43);
        IntervalParser parser = new IntervalParser(day, "9-16.30+20-", now);

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(9).withMinutes(13));
        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void onlyTotalTimeInHoursSpecified() {
        IntervalParser parser = new IntervalParser(day, "7h");

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(7));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void mixedIntervalAndPeriod() {
        IntervalParser parser = new IntervalParser(day, "1h +8-16.30 +1h+ 10m +18-18.05 + 2h30m");

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(13).withMinutes(15));

        assertEquals(expected, parser.getIntervals());
    }

    @Test
    public void intervalPassesOverMidnight() {
        IntervalParser parser = new IntervalParser(day, "22-03.00");

        NormalDay expected = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(5));

        assertEquals(expected, parser.getIntervals());
    }
}
