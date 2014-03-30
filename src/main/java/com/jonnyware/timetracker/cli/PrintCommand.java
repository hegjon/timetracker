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
import java.util.Collections;
import java.util.Map;

public class PrintCommand {
    public void run(Map<String, Object> parsed, TimeEntryParser parser) {
        System.out.printf("Year: %d%n", parser.getYear());
        System.out.println("+------+---------+-----------+");
        System.out.println("| Week |  Total  |    Diff   |");
        System.out.println("+------+---------+-----------+");

        Collection<Interval> entries = parser.listTimeEntries();
        Collection<Vacation> vacations = parser.listVacations();
        DateGroupByWeek vacationByWeek = new DateGroupByWeek(vacations);

        IntervalGroupBy groupBy = new IntervalGroupBy(entries);
        Map<Integer, Collection<Interval>> weeks = groupBy.weekOfYear();

        DefaultWeekdayDurationParser defaultDurationParser = new DefaultWeekdayDurationParser(parsed);
        Map<Integer, Period> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

        IgnoredDays ignoredDays = new IgnoredDays(parser.listVacations(), parser.neutralDays());
        DiffCalculator calculator = new DiffCalculator(hoursPerWeekday, ignoredDays.union());

        Period totalSummed = Period.ZERO;
        Period totalDiff = Period.ZERO;
        for (int week = 1; week <= 53; week++) {
            Map<String, Integer> vacationForThisWeek = vacationByWeek.getComments(week);
            if (!weeks.containsKey(week) && vacationForThisWeek.isEmpty()) {
                continue;
            }
            Collection<Interval> values = Collections.EMPTY_LIST;
            if (weeks.containsKey(week)) {
                values = weeks.get(week);
            }

            Period totalPerWeek = Period.ZERO;
            Period diffPerWeek = Period.ZERO;
            for (Interval interval : values) {
                Period period = interval.toPeriod();
                Period diff = calculator.diff(interval);

                diffPerWeek = diffPerWeek.plus(diff);
                totalDiff = totalDiff.plus(diff);

                totalPerWeek = totalPerWeek.plus(period);
                totalSummed = totalSummed.plus(period);
            }

            System.out.printf("|   %2d | %7s | %9s |", week, Formatter.print(totalPerWeek), Formatter.print(diffPerWeek));
            if (!vacationForThisWeek.isEmpty()) {
                System.out.printf("  %s", vacationForThisWeek);
            }
            System.out.println();
        }
        System.out.println("+------+---------+-----------+");
        System.out.printf("|  Sum | %7s | %9s |%n", Formatter.print(totalSummed), Formatter.print(totalDiff));
        System.out.println("+------+---------+-----------+");
    }
}
