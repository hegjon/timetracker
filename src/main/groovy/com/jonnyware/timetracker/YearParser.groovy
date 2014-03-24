package com.jonnyware.timetracker

import org.joda.time.Interval
import org.joda.time.LocalDate
import org.yaml.snakeyaml.Yaml

class YearParser {
    Map<String, Object> parsed

    YearParser(String content) {
        parsed = new Yaml().load(content)
    }

    YearParser(InputStream input) {
        parsed = new Yaml().load(input)
    }

    int getYear() {
        parsed.get("year")
    }

    public Map<LocalDate, NeutralDay> neutralDays() {
        Map<LocalDate, Vacation> result = new HashMap<>();

        for(Month month: Month.values()) {
            if(!parsed.get(month.getPretty())) {
                continue;
            }

            for(Map.Entry<String, String> entry : parsed.get(month.getPretty())) {
                int dayOfMonth = Integer.valueOf(entry.getKey());
                String value = entry.getValue();

                if(value.startsWith("=")) {
                    LocalDate date = new LocalDate(year, month.getIndex(), dayOfMonth);
                    result.put(date, new NeutralDay(value.substring(1)));
                }
            }
        }

        return result;
    }

    public Map<LocalDate, Vacation> listVacations() {
        Map<LocalDate, Vacation> result = new HashMap<>();

        for(Month month: Month.values()) {
            if(!parsed.get(month.getPretty())) {
                continue;
            }

            for(Map.Entry<String, String> entry : parsed.get(month.getPretty())) {
                int dayOfMonth = Integer.valueOf(entry.getKey());
                String value = entry.getValue();

                if(value.startsWith("+")) {
                    LocalDate date = new LocalDate(year, month.getIndex(), dayOfMonth);
                    result.put(date, new Vacation(value.substring(1)));
                }
            }
        }

        return result;
    }

    public Collection<Interval> listTimeEntries() {
        Collection<Interval> result = new LinkedList<>();

        for(Month month: Month.values()) {
            if(!parsed.get(month.getPretty())) {
                continue;
            }

            for(Map.Entry<String, String> entry : parsed.get(month.getPretty())) {
                int dayOfMonth = Integer.valueOf(entry.getKey());
                String value = entry.getValue();

                if(Character.isDigit(value.codePointAt(0))) {
                    LocalDate date = new LocalDate(year, month.getIndex(), dayOfMonth);
                    Interval interval = new DurationParser(date, value).getDuration();
                    result.add(interval);
                }
            }
        }

        return result;
    }
}
