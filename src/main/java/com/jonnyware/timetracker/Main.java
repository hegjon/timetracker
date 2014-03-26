package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(new FileInputStream(file));
        TimeEntryParser parser = new TimeEntryParser(parsed);
        System.out.println("Year: " + parser.getYear());
        System.out.println("----------------");

        Collection<Interval> entries = parser.listTimeEntries();
        IntervalGroupBy groupBy = new IntervalGroupBy(entries);

        DefaultWeekdayDurationParser defaultDurationParser = new DefaultWeekdayDurationParser(parsed);
        Map<Integer, Duration> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

        DiffCalculator calculator = new DiffCalculator(hoursPerWeekday);

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

            String formatted = HourMinutesFormatter.DEFAULT.print(totalPerWeek.toPeriod());
            String diff = HourMinutesFormatter.DEFAULT.print(diffPerWeek.toPeriod());
            System.out.println("Week " + week.getKey() + ":\t " + formatted + "\t (" + diff + ")");
        }
        System.out.println("----------------");
        String formatted = HourMinutesFormatter.DEFAULT.print(totalSummed.toPeriod());
        String diffTotal = HourMinutesFormatter.DEFAULT.print(totalDiff.toPeriod());
        System.out.println("Total:\t " + formatted + "\t (" + diffTotal + ")");
    }
}
