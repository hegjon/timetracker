package com.jonnyware.timetracker.analyzer;

import com.jonnyware.timetracker.line.TimeEntry;

import java.util.List;

public class DateFormatAnalyzer {
    private final int year;
    private final List<TimeEntry> entries;

    public DateFormatAnalyzer(int year, List<TimeEntry> entries) {
        this.year = year;
        this.entries = entries;
    }

    public DateConverter getDateconverter() {
        return new DayMonthDateConverter(2014);
    }
}
