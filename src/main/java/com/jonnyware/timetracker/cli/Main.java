/*
 *  Copyright 2014 Jonny Heggheim
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.jonnyware.timetracker.cli;

import com.jonnyware.timetracker.TimeEntryParser;
import com.jonnyware.timetracker.cli.PrintCommand;
import org.apache.commons.cli.*;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Option f = OptionBuilder.withArgName("file").withType(String.class).withLongOpt("file").hasArgs(1).create('f');
        Option h = OptionBuilder.withArgName("help").withLongOpt("help").create('h');

        Options options = new Options();
        options.addOption(f);
        options.addOption(h);

        CommandLineParser p = new PosixParser();
        CommandLine cmd = p.parse(options, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jtime", options);
            return;
        }

        String fileName = System.getenv("JTIME_DEFAULT_FILE");
        if (cmd.hasOption("f")) {
            fileName = cmd.getOptionValue("f");
        }
        if (fileName == null) {
            System.err.println("File is not specified!");
            return;
        }
        File file = new File(fileName);

        InputStream input = new FileInputStream(file);
        TimeEntryParser parser = new TimeEntryParser(input, DateTime.now());

        PrintCommand.run(parser);
    }
}
