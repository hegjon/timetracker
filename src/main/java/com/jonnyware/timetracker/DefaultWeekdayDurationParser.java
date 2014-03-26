package com.jonnyware.timetracker;

import org.apache.commons.lang3.Validate;
import org.joda.time.Duration;
import org.joda.time.Period;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DefaultWeekdayDurationParser {
    private final Map<String, Object> parsed;

    public DefaultWeekdayDurationParser(Map<String, Object> parsed) {
        if(parsed == null) {
            this.parsed = Collections.emptyMap();
        } else {
            this.parsed = Collections.unmodifiableMap(parsed);
        }
    }

    public static Map<Integer, Duration> defaultDuration() {
        Map<Integer, Duration> result = new TreeMap<Integer, Duration>();
        result.put(1, Duration.standardHours(8));
        result.put(2, Duration.standardHours(8));
        result.put(3, Duration.standardHours(8));
        result.put(4, Duration.standardHours(8));
        result.put(5, Duration.standardHours(8));
        result.put(6, Duration.ZERO);
        result.put(7, Duration.ZERO);

        return Collections.unmodifiableMap(result);
    }

    public Map<Integer, Duration> getSpecifiedMergedWithDefault() {
        Map<Integer, Duration> result = new TreeMap<Integer, Duration>(defaultDuration());

        if(parsed.containsKey("default")) {
            Map<String, String> weekdays = (Map<String, String>) parsed.get("default");

            for(Weekday weekday : Weekday.values()) {
                if(weekdays.containsKey(weekday.getPretty())) {
                    String specified = weekdays.get(weekday.getPretty());
                    Period period = HourMinutesFormatter.DEFAULT.parsePeriod(specified);
                    result.put(weekday.getIndex(), period.toStandardDuration());
                }
            }
        }

        return Collections.unmodifiableMap(result);
    }
}
