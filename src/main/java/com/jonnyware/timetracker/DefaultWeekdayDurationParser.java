/*
 *  Copyright 2014 Jonny Heggheim
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jonnyware.timetracker;

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

    public static Map<Integer, Period> defaultDuration() {
        Map<Integer, Period> result = new TreeMap<Integer, Period>();
        result.put(1, Period.hours(8));
        result.put(2, Period.hours(8));
        result.put(3, Period.hours(8));
        result.put(4, Period.hours(8));
        result.put(5, Period.hours(8));
        result.put(6, Period.hours(0));
        result.put(7, Period.hours(0));

        return Collections.unmodifiableMap(result);
    }

    public Map<Integer, Period> getSpecifiedMergedWithDefault() {
        Map<Integer, Period> result = new TreeMap<Integer, Period>(defaultDuration());

        if(parsed.containsKey("default")) {
            Map<String, String> weekdays = (Map<String, String>) parsed.get("default");

            for(Weekday weekday : Weekday.values()) {
                if(weekdays.containsKey(weekday.getPretty())) {
                    String specified = weekdays.get(weekday.getPretty());
                    Period period = Formatter.parse(specified);
                    result.put(weekday.getIndex(), period);
                }
            }
        }

        return Collections.unmodifiableMap(result);
    }
}
