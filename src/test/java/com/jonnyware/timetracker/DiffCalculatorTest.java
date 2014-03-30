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

import org.joda.time.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DiffCalculatorTest {
    private DiffCalculator calculator;

    @Before
    public void createCalculator() {
        LocalDate holiday = new LocalDate(2014, 1, 1);
        calculator = new DiffCalculator(DefaultWeekdayDurationParser.defaultDuration(), Collections.singleton(holiday));
    }

    @Test
    public void tenMinutesWorkingOnThursday() {
        Interval i = new Interval(new DateTime(2014, 1, 2, 8, 0), new DateTime(2014, 1, 2, 8, 10));
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

    @Test
    public void twoHoursWorkingOnPublicHoliday() {
        Interval i = new Interval(new DateTime(2014, 1, 1, 8, 0), new DateTime(2014, 1, 1, 10, 0));
        Period actual = calculator.calculateDiff(i);

        assertEquals(Period.hours(2), actual);
    }
}
