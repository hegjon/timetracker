/*
 * Copyright 2014 Jonny Heggheim
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jonnyware.timetracker;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Map;

public class JTimeTotalDiff {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(new FileInputStream(file));
        TimeEntryParser parser = new TimeEntryParser(parsed, DateTime.now());

        Collection<Interval> entries = parser.listTimeEntries();

        DefaultWeekdayDurationParser defaultDurationParser = new DefaultWeekdayDurationParser(parsed);
        Map<Integer, Period> hoursPerWeekday = defaultDurationParser.getSpecifiedMergedWithDefault();

        DiffCalculator calculator = new DiffCalculator(hoursPerWeekday);

        Period total = Period.ZERO;
        for (Interval entry : entries) {
            Period diff = calculator.calculateDiff(entry);
            total.plus(diff);
        }

        String formatted = HourMinutesFormatter.print(total);
        System.out.print(formatted);
    }
}
