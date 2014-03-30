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

import java.util.*;

public class TimeEntryParser {
    private final Map<String, Object> parsed;
    private final DateTime now;

    public TimeEntryParser(Map<String, Object> parsed, DateTime now) {
        this.parsed = parsed;
        this.now = now;
    }

    public Integer getYear() {
        return ((Integer) parsed.get("year"));
    }

    public Collection<NeutralDay> neutralDays() {
        Collection<NeutralDay> result = new LinkedList<NeutralDay>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                int dayOfMonth = entry.getKey();

                if (entry.getValue().startsWith("=")) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    result.add(new NeutralDay(date, entry.getValue().substring(1)));
                }
            }
        }

        return result;
    }

    public Map<LocalDate, Vacation> listVacations() {
        Map<LocalDate, Vacation> result = new HashMap<LocalDate, Vacation>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                Integer dayOfMonth = entry.getKey();
                String value = entry.getValue();

                if (value.startsWith("+")) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    result.put(date, new Vacation(value.substring(1)));
                }
            }
        }

        return result;
    }

    public Collection<Interval> listTimeEntries() {
        Collection<Interval> result = new LinkedList<Interval>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                Integer dayOfMonth = entry.getKey();
                String value = entry.getValue();

                if (Character.isDigit(value.codePointAt(0))) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    Collection<Interval> interval = new IntervalParser(date, value, now).getIntervals();
                    result.addAll(interval);
                }
            }
        }

        return Collections.unmodifiableCollection(result);
    }
}
