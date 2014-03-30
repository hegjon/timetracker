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
package com.jonnyware.timetracker.cli;

import com.jonnyware.timetracker.*;
import org.joda.time.Interval;
import org.joda.time.Period;

import java.util.Collection;
import java.util.Map;

public class TotalDiffCommand {

    public void run(Map<String, Object> parsed, TimeEntryParser parser) {
        try {
            Collection<Interval> entries = parser.listTimeEntries();

            DefaultWeekdayDurationParser defaultDurationParser = new DefaultWeekdayDurationParser(parsed);
            Map<Integer, Period> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

            IgnoredDays ignoredDays = new IgnoredDays(parser.listVacations(), parser.neutralDays());
            DiffCalculator calculator = new DiffCalculator(hoursPerWeekday, ignoredDays.union());

            Period total = Period.ZERO;
            for (Interval entry : entries) {
                Period diff = calculator.calculateDiff(entry);
                total = total.plus(diff);
            }

            String formatted = Formatter.print(total);
            System.out.print(formatted);
        } catch (Exception e) {
            System.err.print(e.getMessage());
        }
    }
}
