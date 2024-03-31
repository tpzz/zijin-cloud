package com.zijin.core.juc;

import java.util.HashMap;
import java.util.Map;

/**
 * JMM (java memory model) java 内存模型，是为了屏蔽各个操作系统对于cpu /内存 以及高速缓存之间的读写调度，
 *
 * 它是一个抽象的一组约定和规范， 通过这个规范定义了程序中（尤其多线程）各个变量的读写方式并决定一个线程对共享变量的
 * 何时写入以及如何变成对另一个线程可见， 围绕多线程的原子性，可见性，有序性展开
 *
 * 1.通过JMM 来实现线程和主内存之间的抽象关系
 * 2.屏蔽各个硬件平台和操作系统的内存访问差异以实现java程序在各个平台下达到一致的内存访问效果
 *
 *
 * volatile 实现 可见性与重排序， 通过内存屏障实现对共享变量的写 对后续所有的读可见
 */
public class JMMTest {


    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
    }
}
