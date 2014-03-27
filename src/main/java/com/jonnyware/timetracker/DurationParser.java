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
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.joda.time.format.PeriodParser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Locale;

public class DurationParser {
    private final String duration;
    private final LocalDate day;

    private static final PeriodFormatter formatter = new PeriodFormatterBuilder()
            .appendHours()
            .appendSeparator(".")
            .appendMinutes()
            .toFormatter();

    public DurationParser(LocalDate day, String duration) {
        this.duration = duration;
        this.day = day;
    }

    public Collection<Interval> getDuration() {
        Collection<Interval> result = new LinkedList<Interval>();
        for (String interval : duration.split(" ")) {
            result.add(getInterval(interval));
        }
        return Collections.unmodifiableCollection(result);
    }

    private Interval getInterval(String interval) {
        String[] splitted = interval.split("-");
        String from = splitted[0];
        String to = splitted[1];
        return new Interval(time(from), time(to));
    }

    private DateTime time(String value) {
        Period p = formatter.parsePeriod(value);
        LocalTime time = new LocalTime(p.getHours(), p.getMinutes());
        return day.toDateTime(time);
    }
}
