package com.jonnyware.timetracker.analyzer;

import com.jonnyware.timetracker.line.TimeEntry;
import org.joda.time.LocalDate;

public class DayMonthDateConverter implements DateConverter {
    private final int year;

    public DayMonthDateConverter(int year) {
        this.year = year;
    }

    @Override
    public LocalDate toDate(TimeEntry timeEntry) {
        return new LocalDate(year, timeEntry.getSecondNumber(), timeEntry.getFirstNumber());
    }
}
