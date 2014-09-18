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

import com.jonnyware.timetracker.analyzer.DateConverter;
import com.jonnyware.timetracker.analyzer.DayMonthDateConverter;
import com.jonnyware.timetracker.line.TimeEntry;
import com.jonnyware.timetracker.line.TimeEntryLineParser;
import com.jonnyware.timetracker.line.YearLineParser;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.io.InputStream;
import java.util.*;

public class TimeEntryParser {
    private final Scanner scanner;
    private final DateTime now;
    private Integer year;
    Map<LocalDate, String> parsed = new TreeMap<LocalDate, String>();

    public TimeEntryParser(InputStream input, DateTime now) {
        this.scanner = new Scanner(input);
        this.now = now;
        parse();
    }

    public TimeEntryParser(String input, DateTime now) {
        this.scanner = new Scanner(input);
        this.now = now;
        parse();
    }

    private void parse() {
        LinkedList<TimeEntry> entries = new LinkedList<TimeEntry>();
        while (scanner.hasNextLine()) {
            String line = LineMassager.massage(scanner.nextLine());
            if (line.isEmpty()) {
                continue;
            }

            if (year == null) {
                year = new YearLineParser(line).getYear();
            } else {
                TimeEntry entry = new TimeEntryLineParser(line).getTimeEntry();
                entries.add(entry);
            }
        }

        DateConverter dateConverter = new DayMonthDateConverter(year);
        for (TimeEntry entry : entries) {
            LocalDate date = dateConverter.toDate(entry);
            parsed.put(date, entry.getText());
        }
    }

    public Integer getYear() {
        return year;
    }

    public Collection<NeutralDay> neutralDays() {
        Collection<NeutralDay> result = new LinkedList<NeutralDay>();

        for (Map.Entry<LocalDate, String> entry : parsed.entrySet()) {
            LocalDate date = entry.getKey();
            String value = entry.getValue();

            if (value.startsWith("=(")) {
                int right = value.indexOf(')');
                if (right > 0) {
                    String period = value.substring(2, right);
                    Period hours = Formatter.parse(period);
                    result.add(new NeutralDay(date, value.substring(right + 1), hours));
                }
            } else if (value.startsWith("=")) {
                result.add(new NeutralDay(date, value.substring(1)));
            }
        }
        return result;
    }

    public Collection<Vacation> listVacations() {
        Collection<Vacation> result = new LinkedList<Vacation>();

        for (Map.Entry<LocalDate, String> entry : parsed.entrySet()) {
            LocalDate date = entry.getKey();
            String value = entry.getValue();
            if (value.startsWith("+")) {
                result.add(new Vacation(date, value.substring(1)));
            }
        }

        return result;
    }

    public Collection<NormalDay> listTimeEntries() {
        Collection<NormalDay> result = new LinkedList<NormalDay>();

        for (Map.Entry<LocalDate, String> entry : parsed.entrySet()) {
            LocalDate date = entry.getKey();
            String value = entry.getValue();

            if (Character.isDigit(value.codePointAt(0))) {
                NormalDay interval = new IntervalParser(date, value, now).getIntervals();
                result.add(interval);
            }
        }

        return Collections.unmodifiableCollection(result);
    }
}
