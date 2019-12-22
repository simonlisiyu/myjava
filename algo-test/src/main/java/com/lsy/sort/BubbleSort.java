package com.lsy.sort;

import java.util.Comparator;

/**
 * 1、每一次排序，经过N-1次比较，把未排序的N个待排序数字中，第1大的移到数组最后面（一撸到底）。
 * 经过第i次排序，就剩下N-i个待排序的数字在数组的最前面。
 * 2、直到待排序的数字只剩下最小的一个。
 * Created by lisiyu on 2019/8/16.
 */
public class BubbleSort {

    /**
     * 第一层循环控制循环轮数 0到n-1
     * 第二层循环每一轮比较次数 0到n-i-1
     * 两两比较做交换，大数往后挪
     * @param list
     * @param comp
     * @param <T>
     */
    public <T> void sort(T[] list, Comparator<T> comp) {
        boolean swapped = true;
        for (int i = 1, len = list.length; i < len && swapped; ++i) {
            swapped = false;
            for (int j = 0; j < len - i; ++j) {
                if (comp.compare(list[j], list[j + 1]) > 0) {
                    T temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                    swapped = true;
                }
            }
        }
    }
}
