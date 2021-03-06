/*
 * Copyright 2014 Jonny Heggheim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jonnyware.timetracker.analyzer;

import com.jonnyware.timetracker.line.TimeEntry;
import org.joda.time.LocalDate;

public class DayMonthDateConverter implements DateConverter {
    private final int year;

    public DayMonthDateConverter(int year) {
        this.year = year;
    }

    @Override
    public LocalDate toDate(TimeEntry timeEntry) {
        return new LocalDate(year, timeEntry.getSecondNumber(), timeEntry.getFirstNumber());
    }
}
