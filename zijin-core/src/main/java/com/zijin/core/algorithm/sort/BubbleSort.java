package com.zijin.core.algorithm.sort;

import com.zijin.core.algorithm.SortConstant;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {

    /**
     * 冒泡排序，每次将n长度的数组中最大值放在末尾， 时间复杂度 O(n2)
     *
     * @param arr 待排序的数组
     */
    public static void bubbleSort(int[] arr) {
        for (int i = arr.length-1; i>0; i--) {
            for (int j = 0; j<i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }


    /**
     * 优化点
     * @param arr
     */
    public static void bubbleSort2(int[] arr) {


    }




    public static void main(String[] args) {
        bubbleSort(SortConstant.param);

        Arrays.stream(SortConstant.param).forEach(System.out::println);
    }
}
