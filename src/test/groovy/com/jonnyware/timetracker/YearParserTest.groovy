package com.jonnyware.timetracker

import org.joda.time.Interval
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.junit.Test

class YearParserTest {
    @Test
    void all() {
        def content =
'''\
year: 2014

january:
 01: =Public holiday
 02: 10.30-17.00
 03: 10.35-18.30
 05: 18.20-22.20

 06: 10.20-17.00
 07: 10.20-18.20
 08: 10.25-17.45
 09: 11.55-18.00
 10: 11.00-16.50
 12: 13.00-15.30

 13: 10.20-17.00
 14: 10.25-17.10
 15: 10.20-17.00
 16: 10.20-17.35
 17: 10.30-17.00

 20: +Gran Canaria
 21: +Gran Canaria
 22: +Gran Canaria
 23: +Gran Canaria
 24: +Gran Canaria

 27: 10.35-17.00
 28: 09.50-17.30
 29: 10.20-17.05
 30: 10.00-17.45
 31: 10.35-16.50

february:
 03: 10.20-17.00
'''

        def parser = new YearParser(content)
        assert parser.year == 2014
    }

    @Test
    void oneDay() {
        def content =
'''\
year: 2014

january:
 1:  08.00-16.00
'''

        def parser = new YearParser(content)
        def day = new LocalDate(2014, 1, 1)
        def from = day.toDateTime(new LocalTime(8, 0))
        def to = day.toDateTime(new LocalTime(16, 0))
        assert parser.entries.get(day) == new Interval(from, to)
    }

    @Test
    void holiday() {
        def content =
            '''\
year: 2014

january:
 1:  =Public holiday
'''

        def parser = new YearParser(content)
        assert parser.entries == [(new LocalDate(2014, 1, 1)) : new NeutralDay("Public holiday")]
    }

    @Test
    void vacation() {
        def content =
            '''\
year: 2014

january:
 1:  +Skiing
'''

        def parser = new YearParser(content)
        assert parser.entries == [(new LocalDate(2014, 1, 1)) : new Vacation("Skiing")]
    }


    @Test
    void empty() {
        def content =
            '''\
year: 2014
'''

        def parser = new YearParser(content)
        assert parser.year == 2014
        assert parser.entries == [:]
    }

    @Test
    void moreMonths() {
        def content =
            '''\
year: 2014

may:
 17:  10.30-18.00
'''

        def parser = new YearParser(content)
        def day = new LocalDate(2014, 5, 17)
        def start = day.toDateTime(new LocalTime(10, 30))
        def end = day.toDateTime(new LocalTime(18, 0))
        def actual = parser.entries
        assert actual.get(day) == new Interval(start, end)
    }
}
