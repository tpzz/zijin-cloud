package com.zijin.core.juc;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;


/**
 * 每个Thread 在set私有变量的时候，都会初始化一个ThreadLocalMap,
 * 这个ThreadLocalMap 里面是一个软引用 Entry, K:是ThreadLocal对象， value是当前线程设置的变量
 * ThreadLocalMap 是ThreadLocal的一个静态内部类
 *
 * 所以在每个线程中使用ThreadLocal获取当前线程的变量，是根据当前线程获取他的ThreadLocalMap, 在ThreadLocalMap中再去根据ThreadLocal作为key
 * 去获取变量值
 *
 */
public class ThreadLocalTest {


    private int age;

    private ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 1);

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

   public void addAge(int age) {
        threadLocal.set(age);
        this.age = this.age+age;
   }


    public ThreadLocal<Integer> getThreadLocal() {
        return threadLocal;
    }

    public void setThreadLocal(ThreadLocal<Integer> threadLocal) {
        this.threadLocal = threadLocal;
    }

    public static void main(String[] args) {

        ThreadLocalTest test = new ThreadLocalTest();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i =0; i<10;i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    int num = finalI;
                    System.out.println(Thread.currentThread().getName() + " add age:" + num);
                    test.addAge(num);
                    System.out.println(Thread.currentThread().getName() + " threadLocal:" + test.getThreadLocal().get());
                    countDownLatch.countDown();

                } finally {
                    test.getThreadLocal().remove();
                }

            });
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("age is " + test.getAge());


    }





}
