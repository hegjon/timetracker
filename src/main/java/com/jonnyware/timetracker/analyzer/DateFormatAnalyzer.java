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

import java.util.List;

public class DateFormatAnalyzer {
    private final int year;
    private final List<TimeEntry> entries;

    public DateFormatAnalyzer(int year, List<TimeEntry> entries) {
        this.year = year;
        this.entries = entries;
    }

    public DateConverter getDateconverter() {
        return new DayMonthDateConverter(2014);
    }
}
