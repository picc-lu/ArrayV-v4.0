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


final public class WaitAndTraverseSort extends Sort {
    public WaitAndTraverseSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("Wait & Traverse");
        this.setRunAllSortsName("Wait & TraverseSort");
        this.setRunSortName("Wait & TraverseSort");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    public void waitAndWatchSort(int[] array, int length, double sleep, boolean auxwrite) {
        int minLeft = 0, maxRight = length - 1;
        boolean isSwapped;
        while (minLeft < length) {
            isSwapped = false;
            int left = minLeft, right = minLeft + 1;
            while (right <= maxRight) {
                boolean leftMove = false;
                if (Reads.compareIndices(array, left, right, sleep, true) == 1) {
                    Writes.swap(array, left, right, sleep, true, false);
                    leftMove = true;
                    isSwapped = true;
                }
                if (leftMove) {
                    left++;
                }
                right++;
            }
            if (!isSwapped) {
                minLeft++;
            }
        }
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        waitAndWatchSort(array, length, 0.01, false);
    }
}