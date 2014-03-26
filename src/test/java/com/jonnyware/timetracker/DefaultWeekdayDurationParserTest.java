package com.jonnyware.timetracker;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DefaultWeekdayDurationParserTest {
    private DefaultWeekdayDurationParser parser(String content) {
        Map<String, Object> parsed = (Map<String, Object>) new Yaml().load(content);
        return new DefaultWeekdayDurationParser(parsed);
    }

    @Test
    public void nullShouldBeThreatedAsEmpty() {
        Map<Integer, Duration> actual = new DefaultWeekdayDurationParser(null).getSpecifiedMergedWithDefault();
        assertEquals(7, actual.size());
    }

    @Test
    public void emptyShouldReturnDefault() {
        DefaultWeekdayDurationParser parser = parser("");
        Map<Integer, Duration> actual = parser.getSpecifiedMergedWithDefault();

        assertEquals(Duration.standardHours(8), actual.get(1));
        assertEquals(Duration.ZERO, actual.get(7));

        assertFalse(actual.containsKey(0));
        assertFalse(actual.containsKey(8));
    }

    @Test
    public void overrideOneDay() {
        String content = "default:\n" +
                " monday: 9h30m\n";

        DefaultWeekdayDurationParser durationParser = parser(content);
        Map<Integer, Duration> actual = durationParser.getSpecifiedMergedWithDefault();
        assertEquals(7, actual.size());
        assertEquals(Period.hours(9).withMinutes(30).toStandardDuration(), actual.get(1));
        assertEquals(Duration.standardHours(8), actual.get(2));
        assertEquals(Duration.ZERO, actual.get(7));
    }
}
