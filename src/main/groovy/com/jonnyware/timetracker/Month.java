package com.jonnyware.timetracker;

public enum Month {
    JANUARY("january"),
    FEBRUARY("february"),
    MARCH("march"),
    APRIL("april"),
    MAY("may"),
    JUNE("june"),
    JULY("july"),
    AUGUST("august"),
    SEPTEMBER( "september"),
    OCTOBER("october"),
    NOVEMBER( "november"),
    DECEMBER( "december");
    private final String pretty;

    private Month(String pretty) {
        this.pretty = pretty;
    }

    public int getIndex() {
        return ordinal() +1;
    }

    public String getPretty() {
        return pretty;
    }
}
