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

    final months = [
            0: "january",
            1: "february",
            2: "march",
            3: "april",
            4: "may",
            5: "june",
            6: "july",
            7: "august",
            8: "september",
            9: "october",
            10: "november",
            11: "december"
    ]

    Map<LocalDate, java.lang.Object> getEntries() {
        months.collectEntries { index, name ->
            durationsForOneMonth(index, name)
        }
    }

    private Map<LocalDate, java.lang.Object> durationsForOneMonth(int index, String name) {
        if(!parsed.containsKey(name)) {
            return [:]
        }

        Map<String, String> month = parsed.get(name)

        month.collectEntries { day, duration ->
            def date = new LocalDate(year, index+1, day.toInteger())

            if(duration.startsWith("=")) {
                [date, new NeutralDay(duration.substring(1))]
            } else if(duration.startsWith("+")) {
                [date, new Vacation(duration.substring(1))]
            } else {
                [date, new DurationParser(date, duration).getDuration()]
            }
        }
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
