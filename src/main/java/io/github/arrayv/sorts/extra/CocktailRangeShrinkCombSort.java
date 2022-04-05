package io.github.arrayv.sorts.extra;


import io.github.arrayv.main.ArrayVisualizer;
import io.github.arrayv.sorts.templates.Sort;


final public class CocktailRangeShrinkCombSort extends Sort {
    public CocktailRangeShrinkCombSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);

        this.setSortListName("Cocktail Range Shrink Comb Sort");
        this.setRunAllSortsName("Cocktail Range Shrink Comb Sort");
        this.setRunSortName("Cocktail Range Shrink Comb Sort");
        this.setCategory("Extra Sorts");

        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }

    @Override
    public void runSort(int[] array, int length, int part) throws Exception {
        double sleep = 1;
        for (int start = 0, end = length; end > start; ) {
            int middle = (start + end) >> 1;
            for (int i = start; i < end; i++) {
                if (Reads.compareIndices(array, i, middle, sleep, false) == 1) {
                    Writes.swap(array, i, middle, sleep, true, false);
                }
                middle++;
                if (middle >= end) {
                    middle = (i + end) >> 1;
                }
            }
            end--;

            middle = (start + end) >> 1;
            for (int i = end - 1; i > start; i--) {
                if (Reads.compareIndices(array, i, middle, sleep, false) == -1) {
                    Writes.swap(array, i, middle, sleep, true, false);
                }
                middle--;
                if (middle < start) {
                    middle = (i + start) >> 1;
                }
            }
            start++;
        }
    }
}
