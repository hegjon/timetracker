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
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class PrintCommand {
    public void run(Map<String, Object> parsed, TimeEntryParser parser) {
        System.out.println("Year: " + parser.getYear());
        System.out.println("----------------");

        Collection<Interval> entries = parser.listTimeEntries();
        Map<LocalDate, Vacation> vacations = parser.listVacations();
        DateGroupByWeek vacationByWeek = new DateGroupByWeek(vacations);

        IntervalGroupBy groupBy = new IntervalGroupBy(entries);
        Map<Integer, Collection<Interval>> weeks = groupBy.weekOfYear();

        DefaultWeekdayDurationParser defaultDurationParser = new DefaultWeekdayDurationParser(parsed);
        Map<Integer, Period> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

        DiffCalculator calculator = new DiffCalculator(hoursPerWeekday);

        Period totalSummed = Period.ZERO;
        Period totalDiff = Period.ZERO;
        for (int weekNumber = 1; weekNumber <= 53; weekNumber++) {
            Map<String, Integer> vacationForThisWeek = vacationByWeek.getComments(weekNumber);
            if (!weeks.containsKey(weekNumber) && vacationForThisWeek.isEmpty()) {
                continue;
            }
            Collection<Interval> values = Collections.EMPTY_LIST;
            if (weeks.containsKey(weekNumber)) {
                values = weeks.get(weekNumber);
            }

            Period totalPerWeek = Period.ZERO;
            Period diffPerWeek = Period.ZERO;
            for (Interval interval : values) {
                Period period = interval.toPeriod();
                Period diff = calculator.calculateDiff(interval);

                diffPerWeek = diffPerWeek.plus(diff);
                totalDiff = totalDiff.plus(diff);

                totalPerWeek = totalPerWeek.plus(period);
                totalSummed = totalSummed.plus(period);
            }

            String formatted = HourMinutesFormatter.print(totalPerWeek);
            String diff = HourMinutesFormatter.print(diffPerWeek);

            System.out.print("Week " + weekNumber + ":\t " + formatted + "\t (" + diff + ")");
            if (!vacationForThisWeek.isEmpty()) {
                System.out.print("\t " + vacationForThisWeek.toString());
            }
            System.out.println();
        }
        System.out.println("----------------");
        String formatted = HourMinutesFormatter.print(totalSummed);
        String diffTotal = HourMinutesFormatter.print(totalDiff);
        System.out.println("Total:\t " + formatted + "\t (" + diffTotal + ")");
    }
}
