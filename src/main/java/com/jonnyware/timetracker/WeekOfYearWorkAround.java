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

import org.joda.time.LocalDate;
import org.joda.time.base.AbstractDateTime;

public class WeekOfYearWorkAround {
    public static int get(LocalDate date) {
        if(date.getWeekOfWeekyear() == 1 && date.getMonthOfYear() == 12) {
            return 53;
        } else {
            return date.getWeekOfWeekyear();
        }
    }
}
