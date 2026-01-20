package ru.solovily;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileFilter {
    private final CliOptions options;

    public FileFilter(CliOptions options) {
        this.options = options;
    }

    public void process() {
        if (!Files.exists(Paths.get(options.outputPath()))) {
            System.err.println(options.outputPath() + " (Output path doesn't exist)");
            return;
        }
        StatsManager statsManager = new StatsManager();
        LineParser lineParser = new LineParser(statsManager);
        LineWriter lineWriter = new LineWriter(options.appendMode(), options.outputPath(), options.prefix());

        for (String inputFile : options.inputFiles()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line = reader.readLine();
                while (line != null) {
                        DataType type = lineParser.parse(line);
                    try {
                        lineWriter.write(type, line);
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        switch (options.statsMode()) {
            case SHORT -> {
                if (statsManager.getIntsCount() > 0) {
                    System.out.println("Integers: " + statsManager.getIntsCount());
                }

                if (statsManager.getDecsCount() > 0) {
                    System.out.println("Decimals: " + statsManager.getDecsCount());
                }

                if (statsManager.getStrCount() > 0) {
                    System.out.println("Strings: " + statsManager.getStrCount());
                }
            }
            case FULL -> {
                if (statsManager.getIntsCount() > 0) {
                    System.out.println(
                            "Integers: count=" + statsManager.getIntsCount() +
                            ", min=" + statsManager.getMinInt() +
                            ", max=" + statsManager.getMaxInt() +
                            ", sum=" + statsManager.getSumInt() +
                            ", avg=" + statsManager.getAvgInt()
                    );
                }

                if (statsManager.getIntsCount() > 0) {
                    System.out.println(
                            "Decimals: count=" + statsManager.getDecsCount() +
                            ", min=" + statsManager.getMinDec() +
                            ", max=" + statsManager.getMaxDec() +
                            ", sum=" + statsManager.getSumDec() +
                            ", avg=" + statsManager.getAvgDec()
                    );
                }

                if (statsManager.getStrCount() > 0) {
                    System.out.println("Strings: count=" + statsManager.getStrCount() +
                            ", min_len=" + statsManager.getMinStrLen() +
                            ", max_len=" + statsManager.getMaxStrLen()
                    );
                }
            }
        }
    }
}
