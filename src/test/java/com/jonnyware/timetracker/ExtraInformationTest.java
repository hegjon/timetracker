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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExtraInformationTest {
    @Test
    public void normalWeek() {
        int actual = new ExtraInformation(2014).daysInWeek(2);
        assertEquals(7, actual);
    }

    @Test
    public void firstWeekOfYear2014HaveOnly5Days() {
        int actual = new ExtraInformation(2014).daysInWeek(1);
        assertEquals(5, actual);
    }

    @Test
    public void lastWeekOfYear2014HaveOnly3Days() {
        int actual = new ExtraInformation(2014).daysInWeek(53);
        assertEquals(3, actual);
    }
}
