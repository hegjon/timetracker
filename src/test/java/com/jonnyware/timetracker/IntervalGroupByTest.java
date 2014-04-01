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
import org.joda.time.Period;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalGroupByTest {
    @Test
    public void empty() {
        IntervalGroupBy groupBy = new IntervalGroupBy(Collections.<NormalDay>emptyList());
        assertTrue(groupBy.weekOfYear().isEmpty());
    }

    @Test
    public void many() {
        NormalDay i1 = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(1));
        NormalDay i2 = new NormalDay(new LocalDate(2014, 1, 2), Period.hours(2));

        NormalDay i3 = new NormalDay(new LocalDate(2014, 1, 6), Period.hours(3));
        NormalDay i4 = new NormalDay(new LocalDate(2014, 1, 7), Period.hours(4));
        NormalDay i5 = new NormalDay(new LocalDate(2014, 1, 12), Period.hours(5));

        Collection<NormalDay> intervals = new LinkedList<NormalDay>();
        intervals.add(i1);
        intervals.add(i2);
        intervals.add(i3);
        intervals.add(i4);
        intervals.add(i5);

        IntervalGroupBy groupBy = new IntervalGroupBy(intervals);
        Collection<NormalDay> week1 = groupBy.weekOfYear().get(1);
        Collection<NormalDay> week2 = groupBy.weekOfYear().get(2);

        assertTrue(week1.contains(i1));
        assertTrue(week1.contains(i2));
        assertFalse(week1.contains(i3));
        assertFalse(week1.contains(i4));
        assertFalse(week1.contains(i5));

        assertFalse(week2.contains(i1));
        assertFalse(week2.contains(i2));
        assertTrue(week2.contains(i3));
        assertTrue(week2.contains(i4));
        assertTrue(week2.contains(i5));
    }

}
