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

import org.joda.time.Period;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DefaultWeekdayDurationParserTest {
    private DefaultWeekdayDurationParser parser(String content) {
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(content);
        return new DefaultWeekdayDurationParser(parsed);
    }

    @Test
    public void nullShouldBeThreatedAsEmpty() {
        Map<Integer, Period> actual = new DefaultWeekdayDurationParser(null).getSpecifiedMergedWithDefault();
        assertEquals(7, actual.size());
    }

    @Test
    public void emptyShouldReturnDefault() {
        DefaultWeekdayDurationParser parser = parser("");
        Map<Integer, Period> actual = parser.getSpecifiedMergedWithDefault();

        assertEquals(Period.hours(8), actual.get(1));
        assertEquals(Period.ZERO, actual.get(7));

        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(8));
    }

    @Test
    public void many() {
        String content = "default:\n" +
                " monday:    9h30m\n" +
                " tuesday:   8h\n" +
                " wednesday: 8h0m\n" +
                " thursday:  0h\n" +
                " saturday:  2h00m\n";

        DefaultWeekdayDurationParser durationParser = parser(content);
        Map<Integer, Period> actual = durationParser.getSpecifiedMergedWithDefault();

        assertEquals(7, actual.size());
        assertEquals(Period.hours(9).withMinutes(30), actual.get(1));
        assertEquals(Period.hours(8).withMinutes(0),  actual.get(2));
        assertEquals(Period.hours(8).withMinutes(0),  actual.get(3));
        assertEquals(Period.hours(0).withMinutes(0),  actual.get(4));
        assertEquals(Period.hours(8).withMinutes(0),  actual.get(5));
        assertEquals(Period.hours(2).withMinutes(0),  actual.get(6));
        assertEquals(Period.hours(0).withMinutes(0),  actual.get(7));
    }
}
