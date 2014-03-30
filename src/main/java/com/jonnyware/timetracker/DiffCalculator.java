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

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.util.Map;
import java.util.Set;

public class DiffCalculator {
    private final Map<Integer, Period> defaultWeekdayDuration;
    private final Set<LocalDate> ignoredDays;

    public DiffCalculator(Map<Integer, Period> defaultWeekdayDuration, Set<LocalDate> ignoredDays) {
        this.defaultWeekdayDuration = defaultWeekdayDuration;
        this.ignoredDays = ignoredDays;
    }

    public Period calculateDiff(Interval interval) {
        if (isIntervalOnIgnoredDay(interval)) {
            return interval.toPeriod();
        }
        int dayOfWeek = interval.getStart().getDayOfWeek();
        Period defaultDuration = defaultWeekdayDuration.get(dayOfWeek);
        return interval.toPeriod().minus(defaultDuration);
    }

    private boolean isIntervalOnIgnoredDay(Interval interval) {
        for (LocalDate holiday : ignoredDays) {
            if (interval.getStart().toLocalDate().equals(holiday)) {
                return true;
            }
        }
        return false;
    }
}
