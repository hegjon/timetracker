package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

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
        System.out.println("----------------");

        Collection<Interval> entries = parser.listTimeEntries();
        IntervalGroupBy groupBy = new IntervalGroupBy(entries);


        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendHours()
                .appendSuffix("h")
                .appendMinutes()
                .appendSuffix("m")
                .toFormatter();

        Duration totalSummed = Duration.ZERO;
        Duration totalDiff = Duration.ZERO;
        for (Map.Entry<Integer, Collection<Interval>> week : groupBy.weekOfYear().entrySet()) {
            Duration totalPerWeek = Duration.ZERO;
            Duration diffPerWeek = Duration.ZERO;
            for (Interval interval : week.getValue()) {
                Duration duration = interval.toDuration();

                Duration p = Duration.standardHours(8);
                if(interval.getStart().getDayOfWeek() >= 6) {
                    p = Duration.ZERO;
                }

                diffPerWeek = diffPerWeek.plus(duration).minus(p);
                totalDiff = totalDiff.plus(duration).minus(p);

                totalPerWeek = totalPerWeek.plus(duration);
                totalSummed = totalSummed.plus(duration);
            }

            String formatted = formatter.print(totalPerWeek.toPeriod());
            String diff = formatter.print(diffPerWeek.toPeriod());
            System.out.println("Week " + week.getKey() + ":\t " + formatted + "\t (" + diff + ")");
        }
        System.out.println("----------------");
        String formatted = formatter.print(totalSummed.toPeriod());
        String diffTotal = formatter.print(totalDiff.toPeriod());
        System.out.println("Total:\t " + formatted + "\t (" + diffTotal + ")");
    }
}
