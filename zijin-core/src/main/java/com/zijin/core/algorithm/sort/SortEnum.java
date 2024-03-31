package com.zijin.core.algorithm.sort;

/**
 * 排序算法
 */
public enum SortEnum {

    BUBBLE_SORT("冒泡排序", BubbleSort.class),
    QUICK_SORT("快速排序", null),
    INSERTION_SORT("插入排序", null),
    SHELL_SORT("shell排序", null),
    SELECTION_SORT("选择排序", null),
    HEAP_SORT("堆排序", null),
    MERGE_SORT("归并排序", null),
    BUCKET_SORT("桶排序", null),
    RADIX_SORT("基数排序", null);

    private final String name;

    private final Class demo;

    SortEnum(String name, Class demo) {
        this.name=name;
        this.demo=demo;
    }



}
