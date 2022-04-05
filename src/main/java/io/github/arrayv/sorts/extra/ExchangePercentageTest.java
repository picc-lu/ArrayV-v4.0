package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;

final public class ExchangePercentageTest extends Sort {

    public ExchangePercentageTest(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("ExchangePercentageTest");
        this.setRunAllSortsName("ExchangePercentageTest");
        this.setRunSortName("ExchangePercentageTest");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    @Override
    public void runSort(int[] array, int length, int bucketCount) throws Exception {
//        final int totalCompare = length * (length - 1) / 2;
        long compareTime = 0, writeTime = 0, emptyTime = 0;
        int compare = 0, write = 0, empty = 0;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
//                long start = System.nanoTime();
//                int i1 = array[i];
//                int j1 = array[j];
//                if (i1 > j1) {
//                }
//                long end = System.nanoTime();
//                compareTime += end - start;
//                compare++;
//                if (compareTime >= 100_000000) {
//                    break;
//                }

                long start = System.nanoTime();
                array[i] = array[j];
                long end = System.nanoTime();
                writeTime += end - start;
                write++;
                if (writeTime >= 10_000000) {
                    break;
                }

//                long start = System.nanoTime();
//                long end = System.nanoTime();
//                emptyTime += end - start;
//                empty++;
//                if (empty >= 377170) {
//                    break;
//                }
            }
//            if (compareTime >= 100_000000) {
//                break;
//            }
            if (writeTime >= 10_000000) {
                break;
            }
//            if (empty >= 377170) {
//                break;
//            }
        }
//        JOptionPane.showMessageDialog(new JFrame(), "needSwap/totalCompare = " + (needSwap * 1.0 / totalCompare), "Info", JOptionPane.OK_OPTION, null);
        System.out.println("compare = " + compare);
        System.out.println("write = " + write);
        System.out.println("emptyTime = " + emptyTime);
//        System.out.println("totalCompare = " + totalCompare);
//        System.out.println("needSwap = " + needSwap);
//        System.out.println("needSwap/totalCompare = " + (needSwap * 1.0 / totalCompare));
    }
}
