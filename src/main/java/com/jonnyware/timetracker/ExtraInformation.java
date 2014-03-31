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

public class ExtraInformation {
    private final int year;

    public ExtraInformation(int year) {
        this.year = year;
    }

    public int daysInWeek(int week) {
        if(week == 1) {
            return 8 - dayOfWeek(1, 1);
        }else if(week == 53) {
            return dayOfWeek(12, 31);
        } else {
            return 7;
        }
    }

    private int dayOfWeek(int monthOfYear, int dayOfMonth) {
        return new LocalDate(year, monthOfYear, dayOfMonth).getDayOfWeek();
    }
}
