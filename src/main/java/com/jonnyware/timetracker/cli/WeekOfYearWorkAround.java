package com.jonnyware.timetracker.cli;

import org.joda.time.LocalDate;
import org.joda.time.base.AbstractDateTime;

public class WeekOfYearWorkAround {
    public static int get(AbstractDateTime date) {
        if(date.getWeekOfWeekyear() == 1 && date.getMonthOfYear() == 12) {
            return 53;
        } else {
            return date.getWeekOfWeekyear();
        }
    }

    public static int get(LocalDate date) {
        if(date.getWeekOfWeekyear() == 1 && date.getMonthOfYear() == 12) {
            return 53;
        } else {
            return date.getWeekOfWeekyear();
        }
    }
}
