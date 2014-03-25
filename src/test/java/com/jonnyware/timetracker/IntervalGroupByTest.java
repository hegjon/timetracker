package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntervalGroupByTest {
    @Test
    public void empty() {
        IntervalGroupBy groupBy = new IntervalGroupBy(Collections.<Interval>emptyList());
        assertTrue(groupBy.weekOfYear().isEmpty());
    }

    @Test
    public void one() {
        Interval i = new Interval(new DateTime(2014, 1, 1, 7, 0), new DateTime(2014, 1, 1, 17, 0));

        IntervalGroupBy groupBy = new IntervalGroupBy(Collections.singletonList(i));
        Collection<Interval> week1 = groupBy.weekOfYear().get(1);
        assertEquals(i, week1.iterator().next());
    }

}
