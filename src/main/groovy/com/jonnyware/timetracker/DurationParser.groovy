package com.jonnyware.timetracker

import groovy.time.TimeDuration

class DurationParser {
    String d
    DurationParser(String duration) {
        d = duration
    }

    TimeDuration getDuration() {
        def (BigDecimal from, BigDecimal to) = d.split("-").collect { it.toBigDecimal() }

        time(to) - time(from)
    }

    TimeDuration time(BigDecimal value) {
        int hour = Math.floor(value)
        int minutes = value.subtract(hour) * 100

        new TimeDuration(hour, minutes, 0, 0)
    }
}
