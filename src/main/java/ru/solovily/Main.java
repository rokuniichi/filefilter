package ru.solovily;

import java.util.Arrays;

public class Main {
    static void main(String[] args) {
        CliParser cliParser = new CliParser();

        if (args.length == 0 ||
                Arrays.asList(args).contains("-?") ||
                Arrays.asList(args).contains("--help")) {
            cliParser.printHelp();
            System.exit(0);
        }

        try {
            CliOptions options = cliParser.parse(args);
            FileFilter fileFilter = new FileFilter(options);
            fileFilter.process();

        } catch (CliParser.CliParseException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
