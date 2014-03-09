package com.jonnyware.timetracker

import groovy.time.TimeDuration
import org.yaml.snakeyaml.Yaml

class YearParser {
    Map<String, Object> parsed

    YearParser(String content) {
        Yaml yaml = new Yaml()
        parsed = yaml.load(content)
    }

    int getYear() {
        parsed.year
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

    Map<Date, TimeDuration> getEntries() {
        months.collectEntries { index, name ->
            durationsForOneMonth(index, name)
        }
    }

    Map<Date, TimeDuration> durationsForOneMonth(int index, String name) {
        if(!parsed.containsKey(name)) {
            return [:]
        }

        Map<String, String> month = parsed.get(name)

        month.collectEntries { day, duration ->
            def date = new Date(year, index, day)

            if(duration.startsWith("=")) {
                [date, new NeutralDay(duration.substring(1))]
            } else if(duration.startsWith("+")) {
                [date, new Vacation(duration.substring(1))]
            } else {
                [date, new DurationParser(duration).duration]
            }
        }

    }
}
