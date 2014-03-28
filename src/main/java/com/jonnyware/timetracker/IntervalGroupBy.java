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

import java.util.*;

public class IntervalGroupBy {
    private final Collection<Interval> intervals;

    public IntervalGroupBy(Collection<Interval> intervals) {
        this.intervals = intervals;
    }

    public Map<Integer, Collection<Interval>> weekOfYear() {
        Map<Integer, Collection<Interval>> weeks = new HashMap<Integer, Collection<Interval>>();

        for (Interval interval : intervals) {
            int week = interval.getStart().getWeekOfWeekyear();
            if(!weeks.containsKey(week)) {
                weeks.put(week, new LinkedList<Interval>());
            }

            weeks.get(week).add(interval);
        }

        return Collections.unmodifiableMap(weeks);
    }
}
