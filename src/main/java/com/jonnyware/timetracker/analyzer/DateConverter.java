package com.jonnyware.timetracker.analyzer;

import com.jonnyware.timetracker.line.TimeEntry;
import org.joda.time.LocalDate;

public interface DateConverter {
    LocalDate toDate(TimeEntry timeEntry);
}
