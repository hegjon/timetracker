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

public class DurationParserTest {
    private final LocalDate day = new LocalDate(2014, 1, 1);

    @Test
    public void hoursAndZeroAppendedMinutes() {
        DurationParser parser = new DurationParser(day, "08.00-16.00");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));

        assertThat(parser.getDuration(), hasItem(expected));
    }

    @Test
    public void hoursAndMinutes() {
        DurationParser parser = new DurationParser(day, "08.10-16.50");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 10), new DateTime(2014, 1, 1, 16, 50));

        assertThat(parser.getDuration(), hasItem(expected));
    }

    @Test
    public void hoursNoMinutes() {
        DurationParser parser = new DurationParser(day, "08-16");
        Interval expected = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));

        assertThat(parser.getDuration(), hasItem(expected));
    }

    @Test
    public void multipleEntries() {
        DurationParser parser = new DurationParser(day, "08.00-16 18-20.10");

        Interval i1 = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 16, 0));
        Interval i2 = new Interval(new DateTime(2014, 1, 1, 18, 0), new DateTime(2014, 1, 1, 20, 10));
        Collection<Interval> actual = parser.getDuration();

        assertThat(actual, hasSize(2));
        assertThat(actual, hasItems(i1, i2));
    }


    @Test
    public void openInterval() {
        DateTime now = new DateTime(2014, 1, 1, 13, 43);
        DurationParser parser = new DurationParser(day, "9.31-", now);

        Interval expected = new Interval(new DateTime(2014, 1, 1, 9, 31), now);

        assertThat(parser.getDuration(), hasItem(expected));
    }
}
