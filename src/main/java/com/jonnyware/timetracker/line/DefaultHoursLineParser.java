package com.jonnyware.timetracker.line;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultHoursLineParser {
    private final static Pattern regex = Pattern.compile("^(\\w+)\\W(.*)$", Pattern.CASE_INSENSITIVE);
    private final String line;

    public DefaultHoursLineParser(String line) {
        this.line = line;
    }

    public int getYear() {
        Matcher matcher = regex.matcher(line);
        if(matcher.find()) {
            return Integer.valueOf(matcher.group(1));
        }
        throw new IllegalArgumentException("Could not parse " + line);
    }
}
