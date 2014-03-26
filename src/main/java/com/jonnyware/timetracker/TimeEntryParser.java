package com.jonnyware.timetracker;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TimeEntryParser {
    private final Map<String, Object> parsed;

    public TimeEntryParser(Map<String, Object> parsed) {
        this.parsed = parsed;
    }

    public Integer getYear() {
        return ((Integer) parsed.get("year"));
    }

    public Map<LocalDate, NeutralDay> neutralDays() {
        Map<LocalDate, NeutralDay> result = new HashMap<LocalDate, NeutralDay>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                int dayOfMonth = entry.getKey();

                if (entry.getValue().startsWith("=")) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    result.put(date, new NeutralDay(entry.getValue().substring(1)));
                }
            }
        }

        return result;
    }

    public Map<LocalDate, Vacation> listVacations() {
        Map<LocalDate, Vacation> result = new HashMap<LocalDate, Vacation>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                Integer dayOfMonth = entry.getKey();
                String value = entry.getValue();

                if (value.startsWith("+")) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    result.put(date, new Vacation(value.substring(1)));
                }
            }
        }

        return result;
    }

    public Collection<Interval> listTimeEntries() {
        Collection<Interval> result = new LinkedList<Interval>();

        for (Month month : Month.values()) {
            if (!parsed.containsKey(month.getPretty())) {
                continue;
            }

            Map<Integer, String> monthEntries = (Map<Integer, String>) parsed.get(month.getPretty());
            for (Map.Entry<Integer, String> entry : monthEntries.entrySet()) {
                Integer dayOfMonth = entry.getKey();
                String value = entry.getValue();

                if (Character.isDigit(value.codePointAt(0))) {
                    LocalDate date = new LocalDate(getYear(), month.getIndex(), dayOfMonth);
                    Interval interval = new DurationParser(date, value).getDuration();
                    result.add(interval);
                }
            }
        }

        return result;
    }
}
