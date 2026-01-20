package ru.solovily;

import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;

import java.io.IOException;
import java.util.Arrays;

public class CliParser {
    private final Options options;

    public CliParser() {
        this.options = createOptions();
    }

    public CliOptions parse(String[] args) throws CliParseException {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(this.options, args);

            StatsMode statsMode = StatsMode.NONE;
            if (cmd.hasOption("s")) {
                statsMode = StatsMode.SHORT;
            } else if (cmd.hasOption("f")) {
                statsMode = StatsMode.FULL;
            }

            boolean appendMode = cmd.hasOption("a");
            String outputDir = cmd.getOptionValue("o", ".");
            String prefix = cmd.getOptionValue("p", "");

            String[] inputFiles = cmd.getArgs();
            if (inputFiles.length == 0) {
                throw new CliParseException("Specify input files\n(Or use \"--help\" for help)");
            }

            return new CliOptions(outputDir, prefix, appendMode, statsMode, Arrays.asList(inputFiles));
        } catch (ParseException e) {
            throw new CliParseException(e.getMessage(), e);
        }
    }

    public void printHelp() {
        String header = "File content filter utility";
        String footer = "Please report issues at https://github.com/rokuniichi/filefilter/issues";

        HelpFormatter helpFormatter = HelpFormatter.builder().setShowSince(false).get();
        try {
            helpFormatter.printHelp("java -jar [name].jar [-s/f] [-a] [-o <PATH>] [-p <PREFIX>] <FILES>", header, this.options, footer, false);
        } catch (IOException e) {
            System.err.println("Error while trying to display help message: " + e.getMessage());
        }
    }

    private Options createOptions() {
        Options options = new Options();

        OptionGroup statsGroup = new OptionGroup();
        statsGroup.addOption(createOption("s", "short", "Toggle short statistics mode (amount only)"));
        statsGroup.addOption(createOption("f", "full", "Toggle full statistics mode (min/max/sum/avg)"));
        statsGroup.setRequired(false);
        options.addOptionGroup(statsGroup);

        options.addOption(createOption("a", "append", "Toggle append mode (default: full rewrite)"));
        options.addOption(createOption("o", "output", "Output files path (default: current directory)", "PATH"));
        options.addOption(createOption("p", "prefix", "Output files' names prefix (default: none)", "PREFIX"));

        return options;
    }

    Option createOption(String shortName, String longName, String description, String argName) {
        return Option.builder(shortName)
                .longOpt(longName)
                .desc(description)
                .hasArg()
                .argName(argName)
                .get();
    }

    Option createOption(String shortName, String longName, String description) {
        return Option.builder(shortName)
                .longOpt(longName)
                .desc(description)
                .get();
    }

    public static class CliParseException extends Exception {
        public CliParseException(String message) {
            super(message);
        }
        public CliParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
