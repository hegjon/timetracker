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
package com.jonnyware.timetracker.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeEntryLineParser {
    private final static Pattern regex = Pattern.compile("^(\\d+)\\W(\\d+)\\W(.*)$");
    private final String line;

    public TimeEntryLineParser(String line) {
        this.line = line;
    }

    public TimeEntry getTimeEntry() {
        Matcher matcher = regex.matcher(line);
        if(matcher.find()) {
            int firstNumber = Integer.valueOf(matcher.group(1));
            int secondNumber = Integer.valueOf(matcher.group(2));
            String text = matcher.group(3).trim();

            return new TimeEntry(firstNumber, secondNumber, text);
        } else {
            throw new IllegalArgumentException("Could not parse " + line);
        }
    }
}
