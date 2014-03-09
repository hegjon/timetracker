package com.jonnyware.timetracker

import groovy.time.Duration
import org.junit.Test

class DurationParserTest {
    @Test
    void onlyHours() {
        def parser = new DurationParser("08.00-16.00")
        assert parser.duration == new Duration(0, 8, 0, 0, 0)
    }

    @Test
    void hoursPlus10Minutes() {
        def parser = new DurationParser("08.00-16.10")
        assert parser.duration == new Duration(0, 8, 10, 0, 0)
    }

    @Test
    void hoursMinus10Minutes() {
        def parser = new DurationParser("08.00-15.50")
        assert parser.duration == new Duration(0, 7, 50, 0, 0)
    }

    @Test
    void hoursMinuds10Minutes() {
        def parser = new DurationParser("08.50-16.00")
        assert parser.duration == new Duration(0, 7, 10, 0, 0)
    }
}
