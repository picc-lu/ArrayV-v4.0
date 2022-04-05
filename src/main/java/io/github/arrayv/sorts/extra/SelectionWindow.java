package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.insert.BinaryInsertionSort;
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
final public class SelectionWindow extends Sort {
    public SelectionWindow(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("SelectionWindow");
        this.setRunAllSortsName("SelectionWindow");
        this.setRunSortName("SelectionWindow");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    public void selectionSort(int[] array, int length, double sleep, boolean auxwrite) {
        int finalWindowSize = 42;
        int windowSize = finalWindowSize;
        int writeIndex = 0;
        int preWriteIndex = 0;
        while (writeIndex < length) {
            int readIndex = writeIndex;
            int minIndex = writeIndex;
            while (readIndex < length) {
                if (Reads.compareIndices(array, readIndex, minIndex, sleep, true) == -1) {
                    minIndex = readIndex;
                }
                windowSize--;
                if (windowSize == 0 || readIndex == length - 1) {
                    if (Reads.compareIndices(array, writeIndex, minIndex, sleep, true) == 1) {
                        Writes.swap(array, writeIndex, minIndex, sleep, true, false);
                    }
                    writeIndex++;
                    windowSize = finalWindowSize;
                    minIndex = readIndex;
                    if (readIndex == length - 1) {
                        BinaryInsertionSort binaryInsertionSort = new BinaryInsertionSort(arrayVisualizer);
                        binaryInsertionSort.customBinaryInsert(array, preWriteIndex, writeIndex, sleep);
                        preWriteIndex = writeIndex;
                    }
                }
                readIndex++;
            }
        }
//            finalWindowSize /= 1.3;
//            if (finalWindowSize < 2) {
//                finalWindowSize = 2;
//            }
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
        selectionSort(array, length, 0.05, false);
//        OptimizedBubbleSort optimizedBubbleSort = new OptimizedBubbleSort(arrayVisualizer);
//        optimizedBubbleSort.runSort(array, length, bucketCount);

//        OptimizedCocktailShakerSort optimizedCocktailShakerSort = new OptimizedCocktailShakerSort(arrayVisualizer);
//        optimizedCocktailShakerSort.runSort(array, length, bucketCount);


    }
}