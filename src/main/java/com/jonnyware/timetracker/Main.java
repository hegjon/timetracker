package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Interval;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        YearParser parser = new YearParser(new FileInputStream(file));
        System.out.println("Year: " + parser.getYear());
        System.out.println("===========");

        Collection<Interval> entries = parser.listTimeEntries();
        IntervalGroupBy groupBy = new IntervalGroupBy(entries);

        for (Map.Entry<Integer, Collection<Interval>> week : groupBy.weekOfYear().entrySet()) {
            Duration d = new Duration(0);
            for (Interval interval : week.getValue()) {
                d = d.plus(interval.toDuration());
            }


            System.out.println("Week " + week.getKey() + ":\t" + d.getStandardHours() + "h");
        }
    }
}
