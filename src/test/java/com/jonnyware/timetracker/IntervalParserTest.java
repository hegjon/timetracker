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
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

public class IntervalParserTest {
    private final LocalDate day = new LocalDate(2014, 1, 1);

    @Test
    public void hoursAndZeroAppendedMinutes() {
        IntervalParser parser = new IntervalParser(day, "08.00-16.00");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));

        assertThat(parser.getIntervals(), hasItem(expected));
    }

    @Test
    public void hoursAndMinutes() {
        IntervalParser parser = new IntervalParser(day, "08.10-16.50");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 10), new DateTime(2014, 1, 1, 16, 50));

        assertThat(parser.getIntervals(), hasItem(expected));
    }

    @Test
    public void hoursNoMinutes() {
        IntervalParser parser = new IntervalParser(day, "08-16");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));

        assertThat(parser.getIntervals(), hasItem(expected));
    }

    @Test
    public void multipleEntries() {
        IntervalParser parser = new IntervalParser(day, "08.00-16 + 18-20.10");

        Interval i1 = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));
        Interval i2 = new Interval(new DateTime(2014, 1, 1, 18, 0), new DateTime(2014, 1, 1, 20, 10));
        Collection<Interval> actual = parser.getIntervals();

        assertThat(actual, hasSize(2));
        assertThat(actual, hasItems(i1, i2));
    }

    @Test
    public void openInterval() {
        DateTime now = new DateTime(2014, 1, 1, 13, 43);
        IntervalParser parser = new IntervalParser(day, "9.31-", now);

        Interval expected = new Interval(new DateTime(2014, 1, 1, 9, 31), now);


        Collection<Interval> actual = parser.getIntervals();
        assertThat(actual, hasSize(1));
        assertThat(actual, hasItem(expected));
    }

    @Test
    public void openClosedAndOneOpenInterval() {
        DateTime now = new DateTime(2014, 1, 1, 21, 43);
        IntervalParser parser = new IntervalParser(day, "9-16.30+20-", now);

        Interval closed = new Interval(new DateTime(2014, 1, 1, 9, 0), new DateTime(2014, 1, 1, 16, 30));
        Interval open = new Interval(new DateTime(2014, 1, 1, 9, 0), new DateTime(2014, 1, 1, 16, 30));

        Collection<Interval> actual = parser.getIntervals();
        assertThat(actual, hasSize(2));
        assertThat(actual, hasItems(closed, open));
    }

    @Test
    public void onlyTotalTimeInHoursSpecified() {
        IntervalParser parser = new IntervalParser(day, "7h");

        Interval open = new Interval(new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 1, 1, 7, 0));

        Collection<Interval> actual = parser.getIntervals();
        assertThat(actual, hasSize(1));
        assertThat(actual, hasItems(open));
    }

    @Test
    public void mixedIntervalAndPeriod() {
        IntervalParser parser = new IntervalParser(day, "1h +8-16.30 +1h+ 10m +18-18.05 + 2h30m");

        Interval period1 = new Interval(new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 1, 1, 1, 0));
        Interval interval1 = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 30));
        Interval period2 = new Interval(new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 1, 1, 1, 0));
        Interval period3 = new Interval(new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 1, 1, 0, 10));
        Interval interval2 = new Interval(new DateTime(2014, 1, 1, 18, 0), new DateTime(2014, 1, 1, 18, 05));
        Interval period4 = new Interval(new DateTime(2014, 1, 1, 0, 0), new DateTime(2014, 1, 1, 2, 30));

        Collection<Interval> actual = parser.getIntervals();
        assertThat(actual, hasSize(6));
        assertThat(actual, hasItems(period1, interval1, period2, period3, interval2, period4));
    }

    @Test
    public void intervalPassesOverMidnight() {
        IntervalParser parser = new IntervalParser(day, "22-03.00");

        Interval expected = new Interval(new DateTime(2014, 1, 1, 22, 0), new DateTime(2014, 1, 2, 3, 0));

        Collection<Interval> actual = parser.getIntervals();
        assertThat(actual, hasSize(1));
        assertThat(actual, hasItem(expected));
    }
}
