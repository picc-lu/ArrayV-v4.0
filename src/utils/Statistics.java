package utils;

import main.ArrayVisualizer;

import java.math.BigDecimal;
import java.text.DecimalFormat;

final public class Statistics {
    private String sortCategory;
    private String sortHeading;
    private String sortExtraHeading;
    private String arrayLength;

    private String sortDelay;
    private String visualTime;
    private String estSortTime;

    private String comparisonCount;
    private String swapCount;
    private String reversalCount;

    private String mainWriteCount;
    private String auxWriteCount;

    private String auxAllocAmount;

    private String segments;

    private BigDecimal readWriteRatioVal;
    private String readWriteRatio;

    private BigDecimal totalCostVal;
    private String totalCost;

    private DecimalFormat formatter;

    public Statistics(ArrayVisualizer ArrayVisualizer) {
        this.formatter = ArrayVisualizer.getNumberFormat();
        this.updateStats(ArrayVisualizer);
    }

    public int[] findSegments(int[] array, int length, boolean reversed) {
        int runs = 1;
        int correct = 0;
        for (int i = 0; i < length - 1; i++) {
            if (!reversed && array[i] > array[i + 1]) runs++;
            else if (reversed && array[i] < array[i + 1]) runs++;
            else correct++;
        }
        int[] result = new int[2];
        result[0] = runs;
        result[1] = (int) ((((double) correct) / (length - 1)) * 100);
        return result;
    }

    public void updateStats(ArrayVisualizer ArrayVisualizer) {
        this.sortCategory = ArrayVisualizer.getCategory();
        this.sortHeading = ArrayVisualizer.getHeading();
        this.sortExtraHeading = ArrayVisualizer.getExtraHeading();
        int showUnique = Math.min(ArrayVisualizer.getUniqueItems(), ArrayVisualizer.getCurrentLength());
        this.arrayLength = this.formatter.format(ArrayVisualizer.getCurrentLength()) + " Numbers"
                + ", " + this.formatter.format(showUnique) + " Unique";

        this.sortDelay = "Delay: " + ArrayVisualizer.getDelays().displayCurrentDelay();
        this.visualTime = "Visual Time: " + ArrayVisualizer.getTimer().getVisualTime();
        this.estSortTime = "Sort Time: " + ArrayVisualizer.getTimer().getRealTime();

        this.comparisonCount = ArrayVisualizer.getReads().getStats();
        this.swapCount = ArrayVisualizer.getWrites().getSwaps();
        this.reversalCount = ArrayVisualizer.getWrites().getReversals();

        this.mainWriteCount = ArrayVisualizer.getWrites().getMainWrites();
        this.auxWriteCount = ArrayVisualizer.getWrites().getAuxWrites();

        this.auxAllocAmount = ArrayVisualizer.getWrites().getAllocAmount();

        int[] shadowarray = ArrayVisualizer.getArray();
        int[] rawSegments = this.findSegments(shadowarray, ArrayVisualizer.getCurrentLength(), ArrayVisualizer.reversedComparator());
        String plural = rawSegments[0] == 1 ? "" : "s";
        this.segments = String.valueOf(rawSegments[1]) + "% Sorted (" + String.valueOf(rawSegments[0]) + " Segment" + plural + ")";

        this.readWriteRatio = "ReadWriteRatio: ";
        this.totalCost = "TotalCost: ";
        long writesLong = ArrayVisualizer.getWrites().getWritesLong();
        BigDecimal c = BigDecimal.valueOf(ArrayVisualizer.getReads().getComparisons());
        BigDecimal w = BigDecimal.valueOf(writesLong);
        if (writesLong == 0L) {
            this.readWriteRatio += "0.0 : 1";
        } else {
            this.readWriteRatioVal = c.divide(w, this.formatter.getMaximumFractionDigits(), this.formatter.getRoundingMode());
            this.readWriteRatio += this.readWriteRatioVal;
            this.readWriteRatio += " : 1";
        }
        this.totalCostVal = c.add(w);
        this.totalCost += this.formatter.format(this.totalCostVal);
    }

    public String getSortIdentity() {
        return this.sortCategory + ": " + this.sortHeading;
    }

    public String getArrayLength() {
        return this.arrayLength + this.sortExtraHeading;
    }

    public String getSortDelay() {
        return this.sortDelay;
    }

    public String getVisualTime() {
        return this.visualTime;
    }

    public String getEstSortTime() {
        return this.estSortTime;
    }

    public String getComparisonCount() {
        return this.comparisonCount;
    }

    public String getSwapCount() {
        return this.swapCount;
    }

    public String getReversalCount() {
        return this.reversalCount;
    }

    public String getMainWriteCount() {
        return this.mainWriteCount;
    }

    public String getAuxWriteCount() {
        return this.auxWriteCount;
    }

    public String getAuxAllocAmount() {
        return this.auxAllocAmount;
    }

    public String getSegments() {
        return this.segments;
    }

    public String getReadWriteRatio() {
        return this.readWriteRatio;
    }

    public BigDecimal getReadWriteRatioVal() {
        return this.readWriteRatioVal;
    }

    public BigDecimal getTotalCostVal() {
        return this.totalCostVal;
    }

    public String getTotalCost() {
        return this.totalCost;
    }
}