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

        return weeks;
    }
}
