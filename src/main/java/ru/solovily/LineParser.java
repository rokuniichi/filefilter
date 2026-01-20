package ru.solovily;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class LineParser {
    private final StatsManager statsManager;

    public LineParser(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    public DataType parse(String line) {
        Scanner scanner = new Scanner(line);
        DataType type   = DataType.STRING;
        if (scanner.hasNextBigInteger()) {
            type = DataType.INTEGER;
            BigInteger num = scanner.nextBigInteger();
            statsManager.recordInt(num);

        } else if (scanner.hasNextBigDecimal()) {
            type = DataType.DECIMAL;
            BigDecimal num = scanner.nextBigDecimal();
            statsManager.recordDecimal(num);
        } else {
            statsManager.recordString(line);
        }

        return type;
    }
}
