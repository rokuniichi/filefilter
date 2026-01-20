package ru.solovily;

import java.util.List;

public record CliOptions(String outputPath, String prefix, boolean appendMode, StatsMode statsMode, List<String> inputFiles) {

}
