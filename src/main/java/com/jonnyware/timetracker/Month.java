package com.jonnyware.timetracker;

public enum Month {
    JANUARY,
    FEBRUARY,
    MARCH,
    APRIL,
    MAY,
    JUNE,
    JULY,
    AUGUST,
    SEPTEMBER,
    OCTOBER,
    NOVEMBER,
    DECEMBER;

    public int getIndex() {
        return ordinal() + 1;
    }

    public String getPretty() {
        return name().toLowerCase();
    }
}
