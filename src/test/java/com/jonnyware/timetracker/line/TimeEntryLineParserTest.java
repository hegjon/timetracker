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

import static org.junit.Assert.*;

public class TimeEntryLineParserTest {
    @Test
    public void whiteSpaceOnly() {
        TimeEntryLineParser parser = new TimeEntryLineParser("1 2 =Public Holiday");
        TimeEntry expected = new TimeEntry(1, 2, "=Public Holiday");
        assertEquals(expected, parser.getTimeEntry());
    }

    @Test
    public void slashAndColons() {
        TimeEntryLineParser parser = new TimeEntryLineParser("1/2: =Public Holiday");
        TimeEntry expected = new TimeEntry(1, 2, "=Public Holiday");
        assertEquals(expected, parser.getTimeEntry());
    }

    @Test
    public void zeroAppended() {
        TimeEntryLineParser parser = new TimeEntryLineParser("01.02| +Day off");
        TimeEntry expected = new TimeEntry(1, 2, "+Day off");
        assertEquals(expected, parser.getTimeEntry());
    }
}