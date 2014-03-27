package com.jonnyware.timetracker;

import org.joda.time.LocalDate;

import java.util.Map;
import java.util.TreeMap;

public class DateGroupByWeek {
    private final Map<LocalDate, Vacation> vacations;

    public DateGroupByWeek(Map<LocalDate, Vacation> vacation) {
        this.vacations = vacation;
    }

    public Map<String, Integer> getComments(int weekNumber) {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (Map.Entry<LocalDate, Vacation> entry : vacations.entrySet()) {
            if(entry.getKey().getWeekOfWeekyear() == weekNumber) {
                String comment = entry.getValue().getComment();
                int count = 0;
                if(result.containsKey(comment)) {
                    count = result.get(comment);
                }

                result.put(comment, ++count);
            }
        }

        return result;
    }
}
