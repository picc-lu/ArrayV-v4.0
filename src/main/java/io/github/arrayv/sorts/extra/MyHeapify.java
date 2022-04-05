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
import io.github.arrayv.utils.Delays;


final public class MyHeapify extends Sort {

    public static void main(String[] args) {
        int leaf = 4, offset = 4;
        int a = (leaf - 1 - offset) / 2 + offset;
        System.out.println("a = " + a);
    }

    public MyHeapify(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("MyHeapify");
        this.setRunAllSortsName("MyHeapify");
        this.setRunSortName("MyHeapify");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
        this.setQuestion("set base:", 4);
    }

    private void shiftDown(int[] array, int root, int end, double sleep, boolean isMax) {
        int compareVal = isMax ? -1 : 1;

        final int offset = root;
        int val = array[root];
        while (true) {
            int leftChild = (root - offset) * 2 + 1 + offset, rightChild = leftChild + 1;
            if (leftChild > end) {
                Writes.write(array, root, val, 0, true, false);
                break;
            }
            int biggerIndex = leftChild;
            if (leftChild < end) {
                if (Reads.compareIndices(array, leftChild, rightChild, 0, true) == compareVal) {
                    biggerIndex = rightChild;
                }
            }
            Delays.sleep(sleep);
            if (Reads.compareValueIndex(array, val, biggerIndex, 0, true) == compareVal) {
                Writes.write(array, root, array[biggerIndex], 0, true, false);
            } else {
                Writes.write(array, root, val, 0, true, false);
                break;
            }
            root = biggerIndex;
        }
    }

    private void shiftUp(int[] array, final int offset, int leaf, double sleep, boolean isMax) {
        int compareVal = isMax ? -1 : 1;

        Writes.changeAuxWrites(1);
        int val = array[leaf];
        int parent;
        while (true) {
            parent = (leaf - 1 - offset) / 2 + offset;
            if (leaf == parent) {
                Writes.write(array, leaf, val, 0, true, false);
                break;
            }
            Delays.sleep(sleep);
            if (Reads.compareIndexValue(array, parent, val, 0, true) == compareVal) {
                Writes.write(array, leaf, array[parent], 0, true, false);
            } else {
                Writes.write(array, leaf, val, 0, true, false);
                break;
            }
            leaf = parent;
        }
    }

    protected void heapify(int[] arr, int start, int end, double sleep, boolean isMax) {
        for (int i = start + 1; i <= end; i++) {
            shiftUp(arr, start, i, sleep, isMax);
        }
    }

    @Override
    public void runSort(int[] array, int length, int base) {
        int start = 0, end = length - 1;
        double sleep = 0.5;
        heapify(array, start, end, sleep, true);

        while (end > 0) {
            Writes.swap(array, 0, end, sleep, true, false);
            end--;
            shiftDown(array, 0, end, sleep, true);
        }
    }
}