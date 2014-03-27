package com.jonnyware.timetracker;

import org.joda.time.Interval;
import org.joda.time.Period;
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
        Map<Integer, Period> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

        DiffCalculator calculator = new DiffCalculator(hoursPerWeekday);

        Period totalSummed = Period.ZERO;
        Period totalDiff = Period.ZERO;
        for (Map.Entry<Integer, Collection<Interval>> week : groupBy.weekOfYear().entrySet()) {
            Period totalPerWeek = Period.ZERO;
            Period diffPerWeek = Period.ZERO;
            for (Interval interval : week.getValue()) {
                Period duration = interval.toPeriod();
                Period diff = calculator.calculateDiff(interval);

                diffPerWeek = diffPerWeek.plus(diff);
                totalDiff = totalDiff.plus(diff);

                totalPerWeek = totalPerWeek.plus(duration);
                totalSummed = totalSummed.plus(duration);
            }

            String formatted = HourMinutesFormatter.print(totalPerWeek);
            String diff = HourMinutesFormatter.print(diffPerWeek);
            System.out.println("Week " + week.getKey() + ":\t " + formatted + "\t (" + diff + ")");
        }
        System.out.println("----------------");
        String formatted = HourMinutesFormatter.print(totalSummed);
        String diffTotal = HourMinutesFormatter.print(totalDiff);
        System.out.println("Total:\t " + formatted + "\t (" + diffTotal + ")");
    }
}
