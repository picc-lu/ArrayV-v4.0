package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;


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
final public class TwoSeqMergeAlgorithm extends Sort {
    public TwoSeqMergeAlgorithm(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("TwoSeqMergeAlgorithm(Not a sort)");
        this.setRunAllSortsName("TwoSeqMergeAlgorithm(Not a sort)");
        this.setRunSortName("TwoSeqMergeAlgorithm(Not a sort)");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    int getMergeDestinationIndex(int start, int end, int i) {
        int middle = (start + end + 1) >> 1;
        if (i < middle) {
            return (i - start) * 2 + start;
        } else {
            return (i - middle) * 2 + start + 1;
        }
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        double sleep = 1;
        int start = 0, end = length - 1;
        int totalSwap = length - 2;
        int[] externalArray = Writes.createExternalArray(length / 2 - 1);
        Writes.arraycopy(array, start + 1, externalArray, 0, externalArray.length, sleep, true, true);
        for (int i = (start + end + 1) >> 1; i <= end - 1; i++) {
            int destinationIndex = getMergeDestinationIndex(start, end, i);
            Writes.write(array, destinationIndex, array[i], sleep, true, false);
        }
        for (int i = 0; i < externalArray.length; i++) {
            int destinationIndex = getMergeDestinationIndex(start, end, start + 1 + i);
            Writes.write(array, destinationIndex, externalArray[i], sleep, true, false);
        }
    }
}