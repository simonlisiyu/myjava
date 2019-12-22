package com.lsy.sort;

/**
 * 二分法插入排序，简称二分排序，
 * 是在插入第i个元素时，对前面的0～i-1元素进行折半，先跟他们中间的那个元素比，
 * 如果小，则对前半再进行折半，否则对后半进行折半，直到left<=right，
 * 然后再把第i个元素前1位与目标位置之间的所有元素后移，再把第i个元素放在目标位置上。
 * 每次排序结束后，0～i-1元素成为有序。
 *
 * 二分法没有排序，只有查找。
 * 所以当找到要插入的位置时。
 * 移动必须从最后一个记录开始，向后移动一位，再移动倒数第2位，直到要插入的位置的记录移后一位。
 * Created by lisiyu on 2019/8/16.
 */
public class HalfSort {

    /**
     * 1、二分法查找插入位置
     * 对前面的0～i-1元素进行折半，先与中间的元素比，
     * 如果小，则对前半再进行折半查找，否则对后半进行折半查找，直到low<=hight。
     * 2、后移
     * 从i-1个元素开始，把元素逐个往后移动，
     * 直到下标为low，将第i个元素插入到第low个元素。
     * @param array
     * @return
     */
    public int[] binarySort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];// 待插入到前面序列的值
            int low = 0;// 序列的左侧
            int hight = i - 1;// 序列的右侧
            int middle = -1;// 序列的中间
            //while循环找到比array[i]大的数组下标low
            while (low <= hight) {

                // middle赋值
                middle = (low + hight)/2;
                if (temp < array[middle]) {
                    hight = middle - 1;
                } else {
                    low = middle + 1;
                }
            }
            for (int j = i - 1; j >= low; j--) {
                // 从i-1到low依次向后移动一位,等待temp值插入
                array[j + 1] = array[j];
            }
            if (low != i) {
                array[low] = temp;
            }
        }
        return array;
    }
}
