package com.lsy.sort;

/**
 * 1.找到数组中最小的那个元素
 * 2.将最小的这个元素和数组中第一个元素交换位置
 * 3.在剩下的元素中找到最小的的元素，与数组第二个元素交换位置
 * 4.重复以上步骤，即可以得到有序数组。
 * Created by lisiyu on 2019/8/16.
 */
public class ChooseSort {

    /**
     * 第一层循环控制循环轮数 i=0到n-1
     * 第二层循环找到剩下i到n-1最小的数
     * 将最小的数和array[i]做交换
     * @param array
     */
    public static void sort(int[] array) {
        for(int i = 0; i < array.length - 1; i++) {// 做第i趟排序
            int k = i;
            for(int j = k + 1; j < array.length; j++){// 选最小的记录
                if(array[j] < array[k]){
                    k = j; //记下目前找到的最小值所在的位置
                }
            }
            //在内层循环结束，也就是找到本轮循环的最小的数以后，再进行交换
            if(i != k){  //交换a[i]和a[k]
                int temp = array[i];
                array[i] = array[k];
                array[k] = temp;
            }
        }
    }

    /**
     * 时间复杂度
     选择排序的交换操作介于 0 和 (n - 1） 次之间。
     选择排序的比较操作为 n (n - 1） / 2 次之间。
     选择排序的赋值操作介于 0 和 3 (n - 1） 次之间。

     比较次数O(n^2），比较次数与关键字的初始状态无关，总的比较次数N=(n-1）+(n-2）+...+1=n*(n-1）/2。
     交换次数O(n），最好情况是，已经有序，交换0次；
     最坏情况交换n-1次，逆序交换n/2次。交换次数比冒泡排序少多了，
     由于交换所需CPU时间比比较所需的CPU时间多，n值较小时，选择排序比冒泡排序快。
     */
}

