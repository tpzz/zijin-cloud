package com.zijin.core.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportTest {

    public static void main(String[] args) {

//        waitTest();

//        conditionAwait();

        lockSupportTest();

    }


    /**
     * 无需同步块,本身就是一把锁， 无需事先加锁，只要成对出现，无需顺序出现，就可以完成wait notify的功能
     *
     * 许可证最多只能发一个，不能累计， 有许可证就可以， 不管许可证是在之前还是之后发放，只要有许可证就不会阻塞
     *
     */
    private static void lockSupportTest() {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " 执行");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + " 被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 唤醒");
            LockSupport.unpark(t1);
        }).start();






    }












    /**
     * condition await signal 与 wait notify 一样，需要与锁配合使用
     *
     * 而且顺序不能颠倒
     */
    private static void conditionAwait() {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 等待");
            try {
                condition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 已唤醒");
            lock.unlock();

        }).start();


        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " 唤醒");
            condition.signal();
            lock.unlock();
        }).start();


    }


    /**
     * wait() 方法会释放当前线程的锁， 通过notify唤醒线程执行
     *
     * wait() 和 notify() 必须在同步方法，必须先加锁
     *
     * 如果notify() 在wait() 之前执行， wait会一直等待
     *
     * 必须先wait 后notify
     */
    private static void waitTest() {
        Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 执行线程");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " 执行完成");
            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 执行线程，唤醒线程0");
                lock.notify();
            }
        }).start();
    }









}
