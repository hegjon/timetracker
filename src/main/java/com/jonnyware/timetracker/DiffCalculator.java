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

    public Period diff(NormalDay interval) {
        LocalDate day = interval.getDay();
        if (ignoredDays.contains(day)) {
            return interval.getTotal();
        } else {
            int dayOfWeek = interval.getDay().getDayOfWeek();
            Period defaultDuration = defaultWeekdayDuration.get(dayOfWeek);
            return interval.getTotal().minus(defaultDuration);
        }
    }
}
