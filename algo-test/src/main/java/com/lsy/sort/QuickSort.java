package com.lsy.sort;

/**
 * 快速排序（Quicksort）是对冒泡排序的一种改进。
 * 基本思想是：
 * 通过一趟排序将要排序的数据分割成独立的两部分，
 * 其中一部分的所有数据都比另外一部分的所有数据都要小，
 * 然后再按此方法对这两部分数据分别进行快速排序，
 * 整个排序过程可以递归进行，以此达到整个数据变成有序序列。
 * Created by lisiyu on 2019/8/16.
 */
public class QuickSort {

    /**
     * 1.切分：将待排序数组，根据某种切分方式（就是选择一个基准数），
     * 将数组切分为两部分，使得前一部分都都小于等于基准数，后一部分都大于基准数。
     * 2.递归：对1中分治出来的两部分分别进行分治。
     * @param array
     * @param low
     * @param high
     * @return
     */

    /**
     * 对于基准位置的选取一般有三种方法：固定切分，随机切分和三取样切分。
     * 固定切分的效率并不是太好，随机切分是常用的一种切分，效率比较高，对于三数取中选择基准点是最理想的一种。

     三取样切分法
     关于这一改进的最简单的描述大概是这样的：与一般的快速排序方法不同，
     它并不是选择待排数组的第一个数作为中轴，而是选用待排数组最左边、最右边和最中间的三个元素的中间值作为中轴。
     这一改进对于原来的快速排序算法来说，主要有两点优势：
     　　（1） 首先，它使得最坏情况发生的几率减小了。
     　　（2） 其次，未改进的快速排序算法为了防止比较时数组越界，在最后要设置一个哨点。
     * @param array
     * @param low
     * @param high
     * @return
     */
    //切分
    public static int partition(int[] array, int low, int high) {
        int key = array[low];
        while (low < high) {
            while (array[high] >= key && high > low) {// 从后半部分向前扫描
                high--;
            }
            array[low] = array[high];
            while (array[low] <= key && high > low) {// 从前半部分向后扫描
                low++;
            }
            array[high] = array[low];
        }
        array[high] = key;
        System.out.println("low="+low+",high="+high);
        System.out.println(arrayToString(array,"排序ing"));
        return high;
    }

    //递归排序
    public static void sort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int index = partition(array, low, high);
        sort(array, low, index - 1);
        sort(array, index + 1, high);
    }


    /**
     * 时间复杂度
     排序算法的最坏情况运行时间为θ（n2），且最坏情况发生在每次划分过程产生的两个区间分别包含n-1个元素和1个元素的时候。
     */




    private static int count;
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        int[] num = {3,45,78,64,52,11,64,55,99,11,18};
        sort(num, 0, num.length-1);
        System.out.println(arrayToString(num,"排序"));
//        System.out.println(arrayToString(num,"未排序"));
//        QuickSort(num,0,num.length-1);
//        System.out.println(arrayToString(num,"排序"));
//        System.out.println("数组个数："+num.length);
//        System.out.println("循环次数："+count);

    }
    /**
     * 快速排序
     * @param num	排序的数组
     * @param left	数组的前针
     * @param right 数组后针
     */
    private static void QuickSort(int[] num, int left, int right) {
        //如果left等于right，即数组只有一个元素，直接返回
        if(left>=right) {
            return;
        }
        //设置最左边的元素为基准值
        int key=num[left];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i=left;
        int j=right;
        while(i<j){
            //j向左移，直到遇到比key小的值
            while(num[j]>=key && i<j){
                j--;
            }
            System.out.println("left="+left+",right="+right+", j="+j);
            //i向右移，直到遇到比key大的值
            while(num[i]<=key && i<j){
                i++;
            }
            System.out.println("left="+left+",right="+right+", i="+i);
            //i和j指向的元素交换
            if(i<j){
                int temp=num[i];
                num[i]=num[j];
                num[j]=temp;
            }
        }
        num[left]=num[i];
        num[i]=key;
        count++;
        System.out.println("count="+count);
        QuickSort(num,left,i-1);
        QuickSort(num,i+1,right);
    }
    /**
     * 将一个int类型数组转化为字符串
     * @param arr
     * @param flag
     * @return
     */
    private static String arrayToString(int[] arr,String flag) {
        String str = "数组为(" + flag + ")：";
        for (int a : arr) {
            str += a + "\t";
        }
        return str;
    }
}
