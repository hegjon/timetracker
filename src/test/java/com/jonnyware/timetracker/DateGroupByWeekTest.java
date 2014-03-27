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

import org.joda.time.LocalDate;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateGroupByWeekTest {
    @Test
    public void empty() {
        DateGroupByWeek dateGroupByWeek = new DateGroupByWeek(Collections.EMPTY_MAP);
        assertTrue(dateGroupByWeek.getComments(1).isEmpty());
    }

    @Test
    public void more() {
        Map<LocalDate,Vacation> vacations = new TreeMap<LocalDate, Vacation>();
        vacations.put(new LocalDate(2014, 1, 1), new Vacation("Test1"));

        vacations.put(new LocalDate(2014, 1, 13), new Vacation("Test2"));
        vacations.put(new LocalDate(2014, 1, 14), new Vacation("Test2"));
        vacations.put(new LocalDate(2014, 1, 19), new Vacation("Test3"));

        DateGroupByWeek dateGroupByWeek = new DateGroupByWeek(vacations);
        assertEquals((Integer)1, dateGroupByWeek.getComments(1).get("Test1"));
        assertEquals((Integer)2, dateGroupByWeek.getComments(3).get("Test2"));
        assertEquals((Integer)1, dateGroupByWeek.getComments(3).get("Test3"));
    }
}
