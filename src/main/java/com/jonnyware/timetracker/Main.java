package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(new FileInputStream(file));
        TimeEntryParser parser = new TimeEntryParser(parsed);
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

        Map<Integer, Duration> defaultWeekdayDuration = new TreeMap<Integer, Duration>();
        defaultWeekdayDuration.put(1, Duration.standardHours(8));
        defaultWeekdayDuration.put(2, Duration.standardHours(8));
        defaultWeekdayDuration.put(3, Duration.standardHours(8));
        defaultWeekdayDuration.put(4, Duration.standardHours(8));
        defaultWeekdayDuration.put(5, Duration.standardHours(8));
        defaultWeekdayDuration.put(6, Duration.ZERO);
        defaultWeekdayDuration.put(7, Duration.ZERO);

        DiffCalculator calculator = new DiffCalculator(defaultWeekdayDuration);

        Duration totalSummed = Duration.ZERO;
        Duration totalDiff = Duration.ZERO;
        for (Map.Entry<Integer, Collection<Interval>> week : groupBy.weekOfYear().entrySet()) {
            Duration totalPerWeek = Duration.ZERO;
            Duration diffPerWeek = Duration.ZERO;
            for (Interval interval : week.getValue()) {
                Duration duration = interval.toDuration();
                Duration diff = calculator.calculateDiff(interval);

                diffPerWeek = diffPerWeek.plus(diff);
                totalDiff = totalDiff.plus(diff);

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
