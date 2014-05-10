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
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeEntryParserTest {
    private TimeEntryParser parser(String content, DateTime now) {
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(content);
        return new TimeEntryParser(parsed, now);
    }

    @Test
    public void oneDay() {
        String content =
                "year: 2014\n" +
                "april:\n" +
                " 1:  08.00-16.00\n";

        TimeEntryParser parser = parser(content, DateTime.now());
        LocalDate day = new LocalDate(2014, 4, 1);
        NormalDay actual = parser.listTimeEntries().iterator().next();
        assertEquals(new NormalDay(day, Period.hours(8)), actual);
    }

    @Test
    public void openDay() {
        String content =
                "year: 2014\n" +
                        "december:\n" +
                        " 19:  09.30-\n";

        DateTime now = new DateTime(2014, 12, 19, 11, 39);
        TimeEntryParser parser = parser(content, now);
        LocalDate day = new LocalDate(2014, 12, 19);
        NormalDay actual = parser.listTimeEntries().iterator().next();
        assertEquals(new NormalDay(day, Period.hours(2).withMinutes(9)), actual);
    }

    @Test
    public void holiday() {
        String content =
                "year: 2014\n" +
                "june:\n" +
                " 10:  =Public holiday\n";

        TimeEntryParser parser = parser(content, DateTime.now());
        LocalDate day = new LocalDate(2014, 6, 10);
        assertEquals(new NeutralDay(day, "Public holiday"), parser.neutralDays().iterator().next());
    }

    @Test
    public void vacation() {
        String content =
                "year: 2014\n" +
                "february:\n" +
                " 27:  +Skiing\n";

        TimeEntryParser parser = parser(content, DateTime.now());
        LocalDate day = new LocalDate(2014, 2, 27);
        assertEquals(new Vacation(day, "Skiing"), parser.listVacations().iterator().next());
    }


    @Test
    public void shouldHandleZeroPrefixForDays() {
        String content = "year: 2014\n" +
                "may:\n" +
                " 08: 10.00-12.00\n";

        TimeEntryParser parser = parser(content, DateTime.now());
        LocalDate day = new LocalDate(2014, 5, 8);
        NormalDay actual = parser.listTimeEntries().iterator().next();
        assertEquals(new NormalDay(day, Period.hours(2)), actual);
    }

    @Test
    public void empty() {
        String content = "year: 2014";

        TimeEntryParser parser = parser(content, DateTime.now());
        assertEquals((Integer)2014, parser.getYear());
        assertTrue(parser.listTimeEntries().isEmpty());
    }
}
