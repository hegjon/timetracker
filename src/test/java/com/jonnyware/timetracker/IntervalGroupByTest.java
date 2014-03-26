package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalGroupByTest {
    @Test
    public void empty() {
        IntervalGroupBy groupBy = new IntervalGroupBy(Collections.<Interval>emptyList());
        assertTrue(groupBy.weekOfYear().isEmpty());
    }

    @Test
    public void many() {
        Interval i1 = new Interval(new DateTime(2014, 1, 1, 7, 0), new DateTime(2014, 1, 1, 17, 0));
        Interval i2 = new Interval(new DateTime(2014, 1, 2, 7, 0), new DateTime(2014, 1, 2, 17, 0));

        Interval i3 = new Interval(new DateTime(2014, 1, 6, 7, 0), new DateTime(2014, 1, 6, 17, 0));
        Interval i4 = new Interval(new DateTime(2014, 1, 7, 7, 0), new DateTime(2014, 1, 7, 17, 0));
        Interval i5 = new Interval(new DateTime(2014, 1, 12, 7, 0), new DateTime(2014, 1, 12, 17, 0));

        Collection<Interval> intervals = new LinkedList<Interval>();
        intervals.add(i1);
        intervals.add(i2);
        intervals.add(i3);
        intervals.add(i4);
        intervals.add(i5);

        IntervalGroupBy groupBy = new IntervalGroupBy(intervals);
        Collection<Interval> week1 = groupBy.weekOfYear().get(1);
        Collection<Interval> week2 = groupBy.weekOfYear().get(2);

        assertTrue(week1.contains(i1));
        assertTrue(week1.contains(i2));
        assertFalse(week1.contains(i3));
        assertFalse(week1.contains(i4));
        assertFalse(week1.contains(i5));

        assertFalse(week2.contains(i1));
        assertFalse(week2.contains(i2));
        assertTrue(week2.contains(i3));
        assertTrue(week2.contains(i4));
        assertTrue(week2.contains(i5));
    }

}
