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
package com.jonnyware.timetracker.line;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class YearLineParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void missingYearKeyword() {
        new YearLineParser("2014").getYear();
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingNumber() {
        new YearLineParser("year: ").getYear();
    }

    @Test
    public void minusYear() {
        int actual = new YearLineParser("year: -2014").getYear();
        assertEquals(-2014, actual);
    }

    @Test
    public void normalYear() {
        int actual = new YearLineParser("year: 2014").getYear();
        assertEquals(2014, actual);
    }

    @Test
    public void normalYear2() {
        int actual = new YearLineParser("Year : 2014").getYear();
        assertEquals(2014, actual);
    }

    @Test
    public void normalYear3() {
        int actual = new YearLineParser("year|2014").getYear();
        assertEquals(2014, actual);
    }
}