package com.jonnyware.timetracker;

public enum Weekday {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public int getIndex() {
        return ordinal() +1;
    }

    public String getPretty() {
        return name().toLowerCase();
    }
}
