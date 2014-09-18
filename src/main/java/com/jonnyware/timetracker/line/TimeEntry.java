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

public class TimeEntry {
    private final int firstNumber;
    private final int secondNumber;
    private final String text;

    public TimeEntry(int firstNumber, int secondNumber, String text) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.text = text;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (firstNumber != timeEntry.firstNumber) return false;
        if (secondNumber != timeEntry.secondNumber) return false;
        if (text != null ? !text.equals(timeEntry.text) : timeEntry.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstNumber;
        result = 31 * result + secondNumber;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
