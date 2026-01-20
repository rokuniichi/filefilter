package ru.solovily;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class StatsManager {
    private static final int          DECIMALS_SCALE = 4;
    private static final RoundingMode ROUNDING_MODE  = RoundingMode.HALF_UP;

    private int        intsCount = 0;
    private BigInteger minInt;
    private BigInteger maxInt;
    private BigInteger sumInt = BigInteger.ZERO;
    private BigInteger avgInt = BigInteger.ZERO;

    private int        decsCount = 0;
    private BigDecimal minDec;
    private BigDecimal maxDec;
    private BigDecimal sumDec = BigDecimal.ZERO;
    private BigDecimal avgDec = BigDecimal.ZERO;

    private int strCount = 0;
    private int minStrSize;
    private int maxStrSize;

    public void recordInt(BigInteger num) {
        if (intsCount == 0) {
            minInt = maxInt = num;
        } else {
            minInt = num.min(minInt);
            maxInt = num.max(maxInt);
        }

        intsCount++;

        sumInt = sumInt.add(num);
        avgInt = sumInt.divide(BigInteger.valueOf(intsCount));
    }

    public void recordDecimal(BigDecimal num) {
        num = num.setScale(DECIMALS_SCALE, ROUNDING_MODE);

        if (decsCount == 0) {
            minDec = maxDec = num;
        } else {
            minDec = num.min(minDec);
            maxDec = num.max(maxDec);
        }

        decsCount++;

        sumDec = sumDec.add(num);
        avgDec = sumDec.divide(BigDecimal.valueOf(decsCount), DECIMALS_SCALE, ROUNDING_MODE);
    }

    public void recordString(String line) {
        if (strCount == 0) {
            minStrSize = maxStrSize = line.length();
        } else {
            minStrSize = Math.min(minStrSize, line.length());
            maxStrSize = Math.max(maxStrSize, line.length());
        }

        strCount++;
    }

    public int getIntsCount() {
        return intsCount;
    }

    public BigInteger getMinInt() {
        return minInt;
    }

    public BigInteger getMaxInt() {
        return maxInt;
    }

    public BigInteger getSumInt() {
        return sumInt;
    }

    public BigInteger getAvgInt() {
        return avgInt;
    }

    public int getDecsCount() {
        return decsCount;
    }

    public BigDecimal getMinDec() {
        return minDec;
    }

    public BigDecimal getMaxDec() {
        return maxDec;
    }

    public BigDecimal getSumDec() {
        return sumDec;
    }

    public BigDecimal getAvgDec() {
        return avgDec;
    }

    public int getStrCount() {
        return strCount;
    }

    public int getMinStrLen() {
        return minStrSize;
    }

    public int getMaxStrLen() {
        return maxStrSize;
    }
}
