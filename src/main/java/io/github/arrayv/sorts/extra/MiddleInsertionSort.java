package io.github.arrayv.sorts.extra;


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

import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

final public class MiddleInsertionSort extends Sort {
    public MiddleInsertionSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("Middle Insertion");
        this.setRunAllSortsName("Middle Insertion Sort");
        this.setRunSortName("Middle Insertion sort");
        this.setCategory("Extra Sorts");
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    protected void insertFromLeft(int[] array, int left, int current, double sleep, boolean auxwrite) {
        int pos = left + 1;

        while (Reads.compareValues(array[pos], current) == -1) {
            Writes.write(array, pos - 1, array[pos], sleep, true, auxwrite);
            pos++;
        }
        Writes.write(array, pos - 1, current, sleep, true, auxwrite);
    }

    protected void insertFromRight(int[] array, int right, int current, double sleep, boolean auxwrite) {
        int pos = right - 1;

        while (Reads.compareValues(array[pos], current) == 1) {
            Writes.write(array, pos + 1, array[pos], sleep, true, auxwrite);
            pos--;
        }
        Writes.write(array, pos + 1, current, sleep, true, auxwrite);
    }

    protected void insertionSort(int[] array, int start, int end, double sleep, boolean auxwrite) {
        int sortedMiddle = (end - start) >> 1;
        int unsortedLeft = sortedMiddle - 1, unsortedRight = sortedMiddle + 1;
        boolean leftTurn = true;

        while (unsortedLeft >= start || unsortedRight < end) {
            sortedMiddle = (unsortedLeft + unsortedRight) >> 1;
            int unsortedNextElementIndex = leftTurn ? unsortedLeft : unsortedRight;
            // 如果即将排序的元素的下标越界，说明那一侧已排序好，继续排另一侧
            if (Reads.compareValues(unsortedNextElementIndex, 0) == -1 ||
                    Reads.compareValues(unsortedNextElementIndex, end) != -1) {
                Highlights.clearAllMarks();
                leftTurn = !leftTurn;
                continue;
            }
            int element = array[unsortedNextElementIndex];
            if (leftTurn && Reads.compareIndices(array, unsortedNextElementIndex, sortedMiddle, sleep, auxwrite) == 1 ||
                    !leftTurn && Reads.compareIndices(array, unsortedNextElementIndex, sortedMiddle, sleep, auxwrite) == -1) {
                if (unsortedLeft >= 0 && unsortedRight < end) {
                    Writes.swap(array, unsortedLeft, unsortedRight, sleep, true, auxwrite);
                    leftTurn = !leftTurn;
                    unsortedNextElementIndex = leftTurn ? unsortedLeft : unsortedRight;
                }
            }
            if (leftTurn) {
                insertFromLeft(array, unsortedNextElementIndex, element, sleep, auxwrite);
                unsortedLeft--;
            } else {
                insertFromRight(array, unsortedNextElementIndex, element, sleep, auxwrite);
                unsortedRight++;
            }
            leftTurn = !leftTurn;
        }

    }

    public void customInsertSort(int[] array, int start, int end, double sleep, boolean auxwrite) {
        this.insertionSort(array, start, end, sleep, auxwrite);
    }

    @Override
    public void runSort(int[] array, int currentLength, int bucketCount) {
        this.insertionSort(array, 0, currentLength, 0.015, false);
    }
}