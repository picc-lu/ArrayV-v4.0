package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

final public class BidirectionalConditionalInsertionSort extends Sort {
    public BidirectionalConditionalInsertionSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("Bidirectional Conditional Insertion Sort");
        this.setRunAllSortsName("Bidirectional Conditional Insertion Sort");
        this.setRunSortName("Bidirectional Conditional Insertion Sort");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    public void insertionSort(int[] array, int from, int to, double sleep, boolean auxwrite) throws Exception {
        for (int i = from + 1; i <= to; i++) {
            int moving = i - 1;
            while (moving >= from) {
                if (Reads.compareIndices(array, moving, i, sleep, true) < 1) {
                    break;
                }
                moving--;
            }
            int insertIndex = moving + 1;
            Writes.cleverMultiSwap(array, i, insertIndex, sleep, true, auxwrite);
        }
    }

    public void insertOneElement(int[] array, int unsortedIndex, int end, double sleep, boolean auxwrite) throws Exception {
        if (unsortedIndex > end) {
            int moving = unsortedIndex - 1;
            while (moving >= end) {
                if (Reads.compareIndices(array, moving, unsortedIndex, sleep, true) < 0) {
                    break;
                }
                moving--;
            }
            int insertIndex = moving + 1;
            Writes.cleverMultiSwap(array, unsortedIndex, insertIndex, sleep, true, auxwrite);
        } else {
            int moving = unsortedIndex + 1;
            while (moving < end) {
                if (Reads.compareIndices(array, moving, unsortedIndex, sleep, true) > 0) {
                    break;
                }
                moving++;
            }
            int insertIndex = moving - 1;
            Writes.cleverMultiSwap(array, unsortedIndex, insertIndex, sleep, true, auxwrite);
        }
    }

    @Override
    public void runSort(int[] array, int length, int part) throws Exception {
        double sleep = 0.01;
        int left = 0, right = length - 1;
        while (left < right) {
            Writes.swap(array, left, (left + right) >> 1, sleep, true, false);
            int readIndex = left + 1;
            if (Reads.compareIndices(array, left, right, sleep, true) == 1) {
                Writes.swap(array, left, right, sleep, true, false);
            }
            while (readIndex < right) {
                if (Reads.compareIndices(array, readIndex, left, sleep, true) <= 0) {
                    Writes.swap(array, readIndex, left + 1, sleep, true, false);
                    Writes.swap(array, left, left + 1, sleep, true, false);
                    insertOneElement(array, left, 0, sleep, false);
                    left++;
                    readIndex++;
                } else if (Reads.compareIndices(array, readIndex, right, sleep, true) >= 0) {
                    Writes.swap(array, readIndex, right - 1, sleep, true, false);
                    Writes.swap(array, right, right - 1, sleep, true, false);
                    insertOneElement(array, right, length, sleep, false);
                    right--;
                } else {
                    readIndex++;
                }
            }
            left++;
            right--;
        }
    }
}
