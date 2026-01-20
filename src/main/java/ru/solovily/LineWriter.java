package ru.solovily;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LineWriter {
    private static final String INTEGERS_FILE_NAME = "integers.txt";
    private static final String DECIMALS_FILE_NAME = "floats.txt";
    private static final String STRINGS_FILE_NAME = "strings.txt";

    private final boolean appendMode;
    private final String outputPath;
    private final String prefix;

    private final Map<DataType, Boolean> hasWrittenToFile = new HashMap<>(Map.of(
            DataType.INTEGER, false,
            DataType.DECIMAL, false,
            DataType.STRING, false
    )
    );
    private final Map<DataType, String> typeToFilename = Map.of(
            DataType.INTEGER, INTEGERS_FILE_NAME,
            DataType.DECIMAL, DECIMALS_FILE_NAME,
            DataType.STRING, STRINGS_FILE_NAME
    );

    public LineWriter(boolean appendMode, String outputPath, String prefix) {
        this.appendMode = appendMode;
        this.outputPath = outputPath;
        this.prefix     = prefix;
    }

    public void write(DataType type, String line) throws IOException {
        boolean currentMode = appendMode || hasWrittenToFile.getOrDefault(type, true);
        String path = outputPath + File.separator + prefix + typeToFilename.getOrDefault(type, STRINGS_FILE_NAME);
        try (FileWriter fileWriter = new FileWriter(path, currentMode)) {
            fileWriter.write(line + System.lineSeparator());
        }
        hasWrittenToFile.put(type, true);
    }
}
