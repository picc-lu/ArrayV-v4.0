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


final public class ThreewaySelectionSort extends Sort {


    public ThreewaySelectionSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("ThreewaySelectionSort");
        this.setRunAllSortsName("ThreewaySelectionSort");
        this.setRunSortName("ThreewaySelectionSort");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    private void innerSort(int[] array, final int from, final int to, final double percent, double sleep) {
        if (from == to) {
            return;
        }
        if (from + 1 == to) {
            if (Reads.compareIndices(array, from, to, sleep, true) == 1) {
                Writes.swap(array, from, to, sleep, true, false);
            }
            return;
        }

        int middle = (int) (((to - from) * percent) + from);
        if (middle == from) {
            middle = from + 1;
        }
//        final int middle = (int) Math.ceil(((to - from) / 2.0) + from);
        int smallArrayMaxIndex = from;
        boolean isNeedFindMin = true;
        int minIndex = middle;
        for (int left = from; left < middle; left++) {
            // 减少比较次数
            if (left > 0 && Reads.compareIndices(array, smallArrayMaxIndex, left, sleep, true) == 1) {
                continue;
            } else {
                smallArrayMaxIndex = left;
            }
            if (isNeedFindMin) {
                isNeedFindMin = false;
                for (int right = middle; right <= to; right++) {
                    if (Reads.compareIndices(array, minIndex, right, sleep, true) == 1) {
                        minIndex = right;
                    }
                }
            }
            if (Reads.compareIndices(array, left, minIndex, sleep, true) == 1) {
                Writes.swap(array, left, minIndex, sleep, true, false);
                minIndex = middle;
                isNeedFindMin = true;
            }
        }
        innerSort(array, from, middle, leftPercent, sleep);
        innerSort(array, middle, to, RightPercent, sleep);
    }

    double leftPercent = 0.5;
    double RightPercent = 0.5;

    public void threewaySelectionSort(int[] array, int length, double sleep, boolean auxwrite) {
        int left = 0, middle1 = length / 3, middle2 = length * 2 / 3, right = length;

        for (int i = left; i < middle1; i++) {
            if (Reads.compareIndices(array, left + i, middle1 + i, sleep, true) == 1)
                Writes.swap(array, left + i, middle1 + i, sleep, true, false);
            if (Reads.compareIndices(array, middle1 + i, middle2 + i, sleep, true) == 1)
                Writes.swap(array, middle1 + i, middle2 + i, sleep, true, false);
            if (Reads.compareIndices(array, left + i, middle1 + i, sleep, true) == 1)
                Writes.swap(array, left + i, middle1 + i, sleep, true, false);
        }
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        threewaySelectionSort(array, length, 1, false);
    }
}