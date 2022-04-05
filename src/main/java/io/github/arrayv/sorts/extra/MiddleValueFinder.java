package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;
import io.github.arrayv.utils.*;

import javax.swing.*;

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
final public class MiddleValueFinder extends Sort {
    public MiddleValueFinder(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("MiddleValueFinder(Not a sort)");
        this.setRunAllSortsName("MiddleValueFinder(Not a sort)");
        this.setRunSortName("MiddleValueFinder(Not a sort)");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    private int middleValueFinder(int[] array, final int from, final int to, double sleep) {
        Tree tree = new Tree();
        TreeUtil treeUtil = new TreeUtil(Reads, Writes, Delays, sleep);
        // find middle value
        for (int i = from; i <= to; i++) {
            Highlights.markArray(1, i);
            treeUtil.addNodeToTree(tree, tree.getRoot(), new Tree.Node(array[i], i));
        }
        Highlights.clearAllMarks();
        final int leftChildren = treeUtil.countLeftChildren(tree.getRoot());
        final int rightChildren = treeUtil.countRightChildren(tree.getRoot());
        if (leftChildren > rightChildren) {
            treeUtil.reconstruct(tree, true);
        }
        return tree.getRoot().getValue();
    }

    private int middleValueFinder2(int[] array, int length, double sleep) {
        int middle = array[0];
        int halfSize = (length - 1) >> 1;
        int smallerThanMiddle = Integer.MIN_VALUE, biggerThanMiddle = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            if (Reads.compareIndexValue(array, i, smallerThanMiddle, sleep, true) == 1 &&
                    Reads.compareIndexValue(array, i, biggerThanMiddle, sleep, true) == -1) {
//                System.out.println("smallerThanMiddle = " + smallerThanMiddle);
//                System.out.println("biggerThanMiddle = " + biggerThanMiddle);
                int small = 0, same = 0, big = 0;
                for (int j = 0; j < length; j++) {
                    if (i != j) {
                        int result = Reads.compareIndices(array, j, i, sleep, true);
                        if (result == 1) {
                            big++;
                        } else if (result == -1) {
                            small++;
                        } else {
                            same++;
                        }
                    }
                }
//                System.out.println("small = " + small);
//                System.out.println("big = " + big);
//                System.out.println("same = " + same);
//                System.out.println("-------------------");
                if (same == 0) {
                    if (small == big || small + 1 == big) {
                        middle = array[i];
                        break;
                    }
                } else if ((small + same >= halfSize || small + same + 1 >= halfSize) && small <= halfSize) {
                    middle = array[i];
                    break;
                }
                if (small > big) {
                    if (Reads.compareIndexValue(array, i, biggerThanMiddle, sleep, true) == -1)
                        biggerThanMiddle = array[i];
                } else {
                    if (Reads.compareIndexValue(array, i, smallerThanMiddle, sleep, true) == 1)
                        smallerThanMiddle = array[i];
                }
            }
        }
        return middle;
    }

    private int middleValueFinder3(int[] array, int length, double sleep) {
        int[] bigElementArray = Writes.createExternalArray(length);
        int[] smallElementArray = Writes.createExternalArray(length);
        for (int i = 0; i < length; i++) {
            int small = 0, same = 0, big = 0;
            int smallElementIndex = 0, bigElementIndex = 0;
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    int result = Reads.compareIndices(array, j, i, sleep, true);
                    if (result == 1) {
                        big++;
                        Writes.write(bigElementArray, bigElementIndex++, j, sleep, true, true);
                    } else if (result == -1) {
                        small++;
                        Writes.write(smallElementArray, smallElementIndex++, j, sleep, true, true);
                    } else {
                        same++;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) {
//        int middleValue = middleValueFinder(array, 0, length - 1, 0.1);
//        System.out.println("middleValue = " + middleValue);
        int middleValue2 = middleValueFinder2(array, length, 0.1);
        JOptionPane.showMessageDialog(this.arrayVisualizer.getMainWindow(), "The middle value is: " + middleValue2, "Result", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("middleValue2 = " + middleValue2);
//        int middleValue3 = middleValueFinder3(array, length, 0.1);
//        System.out.println("middleValue3 = " + middleValue3);
    }
}