package utils;

import java.text.DecimalFormat;

import main.ArrayVisualizer;

/*
 *
MIT License

Copyright (c) 2019 w0rthy
Copyright (c) 2021 ArrayV 4.0 Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 *
 */

final public class Reads {
    private volatile long comparisons;

    private ArrayVisualizer ArrayVisualizer;

    private DecimalFormat formatter;

    private Delays Delays;
    private Highlights Highlights;
    private Timer Timer;

    public Reads(ArrayVisualizer arrayVisualizer) {
        this.ArrayVisualizer = arrayVisualizer;

        this.comparisons = 0;

        this.Delays = ArrayVisualizer.getDelays();
        this.Highlights = ArrayVisualizer.getHighlights();
        this.Timer = ArrayVisualizer.getTimer();

        this.formatter = ArrayVisualizer.getNumberFormat();
    }

    /**
     * Reset all the Reads statistics
     */
    public void resetStatistics() {
        this.comparisons = 0;
    }

    /**
     * Count a comparison
     */
    public void addComparison() {
        this.comparisons++;
    }

    /**
     * Return the comparisons in a nicely formatted string
     * @return The nicely formatted string
     */
    public String getStats() {
        if(this.comparisons < 0) {
            this.comparisons = Long.MIN_VALUE;
            return "Over " + this.formatter.format(Long.MAX_VALUE);
        }
        else {
            if(this.comparisons == 1) return this.comparisons + " Comparison";
            else                      return this.formatter.format(this.comparisons) + " Comparisons";
        }
    }

    /**
     * Returns the number of counted comparisons
     * @return The number of comparisons this sort so far
     */
    public long getComparisons() {
        return this.comparisons;
    }

    /**
     * Sets the number of comparisons made by this sort
     * @param value The number of comparisons
     */
    public void setComparisons(long value) {
        this.comparisons = value;
    }

    /**
     * Compare left to right (may be biased based on stability check and AntiQSort)
     * @param left The left number
     * @param right The right number
     * @return -1 if left < right, 0 if left == right, or 1 if left > right
     * @see Reads#compareOriginalValues(int, int) compareOriginalValues
     * @see Reads#compareIndices(int[], int, int, double, boolean) compareIndices
     */
    public int compareValues(int left, int right) {
        if (ArrayVisualizer.sortCanceled()) throw new StopSort();
        this.comparisons++;

        if (ArrayVisualizer.doingStabilityCheck()) {
            left  = ArrayVisualizer.getStabilityValue(left);
            right = ArrayVisualizer.getStabilityValue(right);
        }

        int cmpVal = 0;

        Timer.startLap("Compare");

        if(left > right)      cmpVal =  1;
        else if(left < right) cmpVal = -1;
        else                  cmpVal =  0;

        Timer.stopLap();

        if (!ArrayVisualizer.useAntiQSort()) {
            return cmpVal;
        }
        else {
            return ArrayVisualizer.antiqCompare(left, right);
        }
    }

    /**
     * Compare left to right (these values should <i>not</i> be from the original array)
     * @param left The left number
     * @param right The right number
     * @return -1 if left < right, 0 if left == right, or 1 if left > right
     * @see Reads#compareValues(int, int) compareValues
     * @see Reads#compareOriginalIndices(int[], int, int, double, boolean) compareOriginalIndices
     */
    public int compareOriginalValues(int left, int right) {
        if (ArrayVisualizer.sortCanceled()) throw new StopSort();
        this.comparisons++;

        int cmpVal = 0;

        Timer.startLap("Compare");

        if(left > right)      cmpVal =  1;
        else if(left < right) cmpVal = -1;
        else                  cmpVal =  0;

        Timer.stopLap();

        return cmpVal;
    }

    /**
     * Compare array[left] to array[right] (may be biased based on stability check and AntiQSort)
     * @param array The array to compare values from
     * @param left The left index in the array
     * @param right The right index in the array
     * @param sleep The amount of milliseconds to pause during the comparison
     * @param mark Whether the indices should be highlighted
     * @return -1 if array[left] < array[right], 0 if array[left] == array[right], or 1 if array[left] > array[right]
     * @see Reads#compareOriginalIndices(int[], int, int, double, boolean) compareOriginalIndices
     * @see Reads#compareValues(int, int) compareValues
     * @see Delays#sleep(double)
     * @see Highlights#markArray(int, int)
     */
    public int compareIndices(int[] array, int left, int right, double sleep, boolean mark) {
        if(mark) {
            Highlights.markArray(1, left);
            Highlights.markArray(2, right);
            Delays.sleep(sleep);
        }
        return this.compareValues(array[left], array[right]);
    }

    /**
     * Compare array[left] to array[right] (these values should <i>not</i> be from the original array)
     * @param array The array to compare values from
     * @param left The left index in the array
     * @param right The right index in the array
     * @param sleep The amount of milliseconds to pause during the comparison
     * @param mark Whether the indices should be highlighted
     * @return -1 if array[left] < array[right], 0 if array[left] == array[right], or 1 if array[left] > array[right]
     * @see Reads#compareIndices(int[], int, int, double, boolean) compareIndices
     * @see Reads#compareOriginalValues(int, int) compareOriginalValues
     * @see Delays#sleep(double)
     * @see Highlights#markArray(int, int)
     */
    public int compareOriginalIndices(int[] array, int left, int right, double sleep, boolean mark) {
        if(mark) {
            Highlights.markArray(1, left);
            Highlights.markArray(2, right);
            Delays.sleep(sleep);
        }
        return this.compareOriginalValues(array[left], array[right]);
    }

    /**
     * Find the highest value in the array
     * @param array The array to find the highest value of
     * @param length The length of the array
     * @param sleep The amount of milliseconds to pause for each element in the array
     * @param mark Whether it should highlight its progress (in blue)
     * @return The highest value in the array
     * @see Reads#analyzeMin(int[], int, double, boolean) analyzeMin
     * @see ArrayVisualizer#toggleAnalysis(boolean)
     */
    public int analyzeMax(int[] array, int length, double sleep, boolean mark) {
        ArrayVisualizer.toggleAnalysis(true);
        ArrayVisualizer.updateNow();

        int max = 0;

        for(int i = 0; i < length; i++) {
            if (ArrayVisualizer.sortCanceled()) throw new StopSort();
			
			int val = array[i];
			if(ArrayVisualizer.doingStabilityCheck())
				val = ArrayVisualizer.getStabilityValue(val);
			
            Timer.startLap("Analysis");

            if(val > max) max = val;

            Timer.stopLap();

            if(mark) {
                Highlights.markArray(1, i);
                Delays.sleep(sleep);
            }
        }

        ArrayVisualizer.toggleAnalysis(false);
        ArrayVisualizer.updateNow();

        return max;
    }

    /**
     * Find the lowest value in the array
     * @param array The array to find the lowest value of
     * @param length The length of the array
     * @param sleep The amount of milliseconds to pause for each element in the array
     * @param mark Whether it should highlight its progress (in blue)
     * @return The lowest value in the array
     * @see Reads#analyzeMax(int[], int, double, boolean) analyzeMax
     * @see ArrayVisualizer#toggleAnalysis(boolean)
     */
    public int analyzeMin(int[] array, int length, double sleep, boolean mark) {
        ArrayVisualizer.toggleAnalysis(true);
        ArrayVisualizer.updateNow();

        int max = 0;

        for(int i = 0; i < length; i++) {
            if (ArrayVisualizer.sortCanceled()) throw new StopSort();
			
			int val = array[i];
			if(ArrayVisualizer.doingStabilityCheck())
				val = ArrayVisualizer.getStabilityValue(val);
			
            Timer.startLap("Analysis");

            if(val < max) max = val;

            Timer.stopLap();

            if(mark) {
                Highlights.markArray(1, i);
                Delays.sleep(sleep);
            }
        }

        ArrayVisualizer.toggleAnalysis(false);
        ArrayVisualizer.updateNow();

        return max;
    }

    /**
     * Find the highest logarithm of the specified base in the array
     * @param array The array to find the highest logarithm of the specified base of
     * @param length The length of the array
     * @param base The base to use
     * @param sleep The amount of milliseconds to pause for each element in the array
     * @param mark Whether it should highlight its progress (in blue)
     * @return The highest logarithm of the specified base in the array
     * @apiNote The logarithm will be truncated/floored (if you want to ceiling instead, use {@link Reads#analyzeMaxCeilingLog(int[], int, int, double, boolean) analyzeMaxCeilingLog})
     * @see Reads#analyzeMaxCeilingLog(int[], int, double, boolean) analyzeMaxCeilingLog
     * @see ArrayVisualizer#toggleAnalysis(boolean)
     */
    public int analyzeMaxLog(int[] array, int length, int base, double sleep, boolean mark) {
        ArrayVisualizer.toggleAnalysis(true);
        ArrayVisualizer.updateNow();

        int max = 0;

        for(int i = 0; i < length; i++) {
            if (ArrayVisualizer.sortCanceled()) throw new StopSort();
			
			int val = array[i];
			if(ArrayVisualizer.doingStabilityCheck())
				val = ArrayVisualizer.getStabilityValue(val);
			
            int log = (int) (Math.log(val) / Math.log(base));

            Timer.startLap("Analysis");

            if(log > max) max = log;

            Timer.stopLap();

            if(mark) {
                Highlights.markArray(1, i);
                Delays.sleep(sleep);
            }
        }

        ArrayVisualizer.toggleAnalysis(false);
        ArrayVisualizer.updateNow();

        return max;
    }

    /**
     * Find the highest logarithm of the specified base in the array
     * @param array The array to find the highest logarithm of the specified base of
     * @param length The length of the array
     * @param base The base to use
     * @param sleep The amount of milliseconds to pause for each element in the array
     * @param mark Whether it should highlight its progress (in blue)
     * @return The highest logarithm of the specified base in the array
     * @apiNote The logarithm will be ceilinged (if you want to truncate/floor instead, use {@link Reads#analyzeMaxLog(int[], int, int, double, boolean) analyzeMaxLog})
     * @see Reads#analyzeMaxLog(int[], int, double, boolean) analyzeMaxLog
     * @see ArrayVisualizer#toggleAnalysis(boolean)
     */
    public int analyzeMaxCeilingLog(int[] array, int length, int base, double sleep, boolean mark) {
        ArrayVisualizer.toggleAnalysis(true);
        ArrayVisualizer.updateNow();

        int max = 0;

        for(int i = 0; i < length; i++) {
            if (ArrayVisualizer.sortCanceled()) throw new StopSort();
			
			int val = array[i];
			if(ArrayVisualizer.doingStabilityCheck())
				val = ArrayVisualizer.getStabilityValue(val);
			
            int log = (int)Math.ceil(Math.log(val) / Math.log(base));

            Timer.startLap("Analysis");

            if(log > max) max = log;

            Timer.stopLap();

            if(mark) {
                Highlights.markArray(1, i);
                Delays.sleep(sleep);
            }
        }

        ArrayVisualizer.toggleAnalysis(false);
        ArrayVisualizer.updateNow();

        return max;
    }

    /**
     * Find the highest bit in the array
     * @param array The array to find the highest bit of
     * @param length The length of the array
     * @param sleep The amount of milliseconds to pause for each element in the array
     * @param mark Whether it should highlight its progress (in blue)
     * @return The highest bit in the array
     * @see Reads#analyzeMaxLog(int[], int, double, boolean)
     * @see ArrayVisualizer#toggleAnalysis(boolean)
     */
    public int analyzeBit(int[] array, int length) {
        ArrayVisualizer.toggleAnalysis(true);
        ArrayVisualizer.updateNow();

        // Find highest bit of highest value
        int max = 0;

        for(int i = 0; i < length; i++) {
            if (ArrayVisualizer.sortCanceled()) throw new StopSort();
			
			int val = array[i];
			if(ArrayVisualizer.doingStabilityCheck())
				val = ArrayVisualizer.getStabilityValue(val);
			
            Timer.startLap("Analysis");

            max = Math.max(max, val);

            Timer.stopLap();

            Highlights.markArray(1, i);
            Delays.sleep(0.75);
        }

        int analysis;

        Timer.startLap();

        analysis = 31 - Integer.numberOfLeadingZeros(max);

        Timer.stopLap();

        ArrayVisualizer.toggleAnalysis(false);
        ArrayVisualizer.updateNow();
        return analysis;
    }

    /**
     * Get the specified digit in the number using the specified base
     * @param a The number to get a digit of
     * @param power The digit index (0 is the rightmost digit)
     * @param radix The base to use (2 is binary, 8 is octal, 10 is decimal, 16 is hexadecimal, etc.)
     * @return The digit in the number (as an int)
     * @see Reads#getBit(int, int) getBit
     */
    public int getDigit(int a, int power, int radix) {
		if(ArrayVisualizer.doingStabilityCheck())
			a = ArrayVisualizer.getStabilityValue(a);
		
        int digit;
        Timer.startLap();
        digit = (int) (a / Math.pow(radix, power)) % radix;
        Timer.stopLap();
        return digit;
    }

    /**
     * Get the specified bit of the number as a boolean
     * @param n The number to get the bit of
     * @param k The index (0 is rightmost) of the bit
     * @return The bit as a boolean
     * @see Reads#getDigit(int, int, int) getDigit
     */
    public boolean getBit(int n, int k) {
		if(ArrayVisualizer.doingStabilityCheck())
			n = ArrayVisualizer.getStabilityValue(n);
		
        // Find boolean value of bit k in n
        boolean result;
        Timer.startLap();
        result = ((n >> k) & 1) == 1;
        Timer.stopLap();
        return result;
    }
}