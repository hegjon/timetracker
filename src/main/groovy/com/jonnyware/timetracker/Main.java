package com.jonnyware.timetracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        YearParser parser = new YearParser(new FileInputStream(file));
        System.out.println(parser.getYear());
    }

}
