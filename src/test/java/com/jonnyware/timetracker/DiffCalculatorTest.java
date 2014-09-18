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
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 2), Period.minutes(10));
        Period actual = calculator.diff(i);
        Period expected = Period.hours(-8).withMinutes(10);

        assertEquals(expected, actual);
    }

    @Test
    public void eighthHoursWorkingOnMonday() {
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 6), Period.hours(8));
        Period actual = calculator.diff(i);

        assertEquals(Period.ZERO, actual);
    }

    @Test
    public void eighthHoursAndTenMinutesWorkingOnFriday() {
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 3), Period.hours(8).withMinutes(10));
        Period actual = calculator.diff(i);

        assertEquals(Period.minutes(10), actual);
    }

    @Test
    public void twoHoursWorkingOnSaturday() {
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 4), Period.hours(2));
        Period actual = calculator.diff(i);

        assertEquals(Period.hours(2), actual);
    }

    @Test
    public void twoHoursWorkingOnPublicHoliday() {
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(2));
        Period actual = calculator.diff(i);

        assertEquals(Period.hours(2), actual);
    }

    @Test
    public void twoHoursWorkingOnHalfPublicHoliday() {
        NormalDay i = new NormalDay(new LocalDate(2014, 1, 1), Period.hours(2));
        Period actual = calculator.diff(i);

        assertEquals(Period.hours(2), actual);
    }
}
