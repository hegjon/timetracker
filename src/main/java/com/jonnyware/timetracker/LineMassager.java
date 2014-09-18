package com.jonnyware.timetracker;

public class LineMassager {
    public static String massage(String line) {
        int index = line.indexOf('#');
        if(index > 0) {
            line = line.substring(0, index);
        }
        return line.trim();
    }
}
