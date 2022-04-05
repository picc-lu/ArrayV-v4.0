package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.insert.BinaryInsertionSort;
import io.github.arrayv.sorts.templates.Sort;
import io.github.arrayv.utils.Delays;
import io.github.arrayv.utils.Highlights;


/*
 * 
MIT License

Copyright (c) 2021 Lu

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
final public class HeapifyType3Sort extends Sort {
    public HeapifyType3Sort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("HeapifyType3Sort");
        this.setRunAllSortsName("HeapifyType3Sort");
        this.setRunSortName("HeapifyType3Sort");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    public void heapifyType3Sort(int[] array, int length, int time, double sleep, boolean auxwrite) {
        int[] offsetConst = new int[]{
                0,
//                700,
//                1000,
//                1400
                100, 400, 500, 1500
        };

        int offsetIndex = 0;

        int offset = offsetConst[offsetIndex++];
        int left = offset, right = length;

        while (left < right) {
            Highlights.markArray(99, left);
            heapify(array, left, right, sleep, false);
            int denominator = 4;
            int innerLeft, innerRight;
            while (denominator <= length / 2) {
                int numerator = 1;
                while (numerator + 2 < denominator) {
                    innerLeft = length * numerator / denominator;
                    innerRight = length * (numerator + 2) / denominator;
                    numerator += 2;
                    if (innerLeft < left) {
                        continue;
                    }
                    heapify(array, innerLeft, innerRight, sleep, false);
                }
                denominator *= 2;
            }
//            Delays.sleep(1500);
//            left = (left + right) >> 1;

            if (offsetIndex >= offsetConst.length) break;

            offset = offsetConst[offsetIndex++];
            left = offset;
        }
        Highlights.clearAllMarks();
        BinaryInsertionSort adaptiveBinaryInsertionSort = new BinaryInsertionSort(arrayVisualizer);
        adaptiveBinaryInsertionSort.runSort(array, length, 0);
//        for (int i = 1; i < length; i++) {
//            binaryInsertSort1Element(array, 0, i, sleep, sleep);
//        }
    }

    protected int binaryInsertSort1Element(int[] array, int start, int end, double compSleep, double writeSleep) {
        int num = array[end];
        int lo = start, hi = end;

        while (lo < hi) {
            int mid = lo + ((hi - lo) / 2); // avoid int overflow!
            Highlights.markArray(1, lo);
            Highlights.markArray(2, mid);
            Highlights.markArray(3, hi);

            Delays.sleep(compSleep);

            if (Reads.compareValues(num, array[mid]) < 0) { // do NOT move equal elements to right of inserted element; this maintains stability!
                hi = mid;
            } else {
                lo = mid + 1;
            }
//            comparison++;
        }

        Highlights.clearMark(3);

        // item has to go into position lo

        int j = end - 1;

        while (j >= lo) {
            Writes.write(array, j + 1, array[j], writeSleep, true, false);
            j--;
//            writes++;
        }
        Writes.write(array, lo, num, writeSleep, true, false);
//        writes++;

        return lo;
    }

    private int reverseDecreasedSubArray(int[] array, int from, int to, double sleep) {
        int start = from, end = start + 1;
        int unsortedIndex = -1;
        Boolean isSubArrayDecrease = Reads.compareIndices(array, from, from + 1, sleep, true) == 1;
        while (end <= to) {
            if (end == to) {
                if (Boolean.TRUE.equals(isSubArrayDecrease)) {
                    Writes.reversal(array, start, end, sleep, true, false);
                    unsortedIndex = unsortedIndex == -1 ? to : unsortedIndex;
                }
                break;
            }
            if (isSubArrayDecrease == null) {
                isSubArrayDecrease = Reads.compareIndices(array, end, end + 1, sleep, true) == 1;
            } else {
                int result = Reads.compareIndices(array, end, end + 1, sleep, true);
                if (isSubArrayDecrease) {
                    if (result == -1) {
                        Writes.reversal(array, start, end, sleep, true, false);
                        start = end + 1;
                        unsortedIndex = unsortedIndex == -1 ? end + 1 : unsortedIndex;
                        isSubArrayDecrease = null;
                    }
                } else {
                    if (result == 1) {
                        start = end + 1;
                        unsortedIndex = unsortedIndex == -1 ? end + 1 : unsortedIndex;
                        isSubArrayDecrease = null;
                    }
                }
            }
            end++;
        }
        return unsortedIndex;
    }

    private void siftDown(int[] array, int root, int dist, int start, double sleep, boolean isMax) {
        int compareVal = isMax ? -1 : 1;

        while (root <= dist / 2) {
            int leaf = 2 * root;
            if (leaf < dist && Reads.compareValues(array[start + leaf - 1], array[start + leaf]) == compareVal) {
                leaf++;
            }
            Highlights.markArray(1, start + root - 1);
            Highlights.markArray(2, start + leaf - 1);
            Delays.sleep(sleep);
            if (Reads.compareValues(array[start + root - 1], array[start + leaf - 1]) == compareVal) {
                Writes.swap(array, start + root - 1, start + leaf - 1, 0, true, false);
                root = leaf;
            } else break;
        }
    }

    private void heapify(int[] arr, int low, int high, double sleep, boolean isMax) {
        int length = high - low;
        for (int i = length / 2; i >= 1; i--) {
            siftDown(arr, i, length, low, sleep, isMax);
        }
    }

    private int getTime(int length) {
        return (int) Math.round((Math.pow(length, -0.369553) - 0.213292) / (Math.pow(length, 0.00192345) - 1.02437));
    }

    @Override
    public void runSort(int[] array, int length, int partition) {
        double sleep = 0.1;
        int time = getTime(length);
        heapifyType3Sort(array, length, time, sleep, false);
    }
}