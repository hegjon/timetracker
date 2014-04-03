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

import com.jonnyware.timetracker.cli.WeekOfYearWorkAround;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class DateGroupByWeek {
    private final Collection<SpecialDay> vacations;

    public DateGroupByWeek(Collection<SpecialDay> vacation) {
        this.vacations = vacation;
    }

    public Map<String, Integer> getComments(int weekNumber) {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        for (SpecialDay entry : vacations) {
            if(WeekOfYearWorkAround.get(entry.getDay()) == weekNumber) {
                String comment = entry.getComment();
                int count = 0;
                if(result.containsKey(comment)) {
                    count = result.get(comment);
                }

                result.put(comment, count + 1);
            }
        }

        return Collections.unmodifiableMap(result);
    }
}
