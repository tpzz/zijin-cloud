package com.zijin.core.juc;


import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 乐观锁与悲观锁， 乐观锁是无锁编程，应用与读多写少的场景，在写的时候进行数据校验；synchronized是悲观锁
 * 1.类对象所， 锁住类的静态资源
 * 2.对象锁，所著对象实例资源
 * 3.方法锁，静态方法枷锁，其实是类对象所， 非静态方法，锁的是对象实例的资源
 * 4.代码块，
 * 5.偏向锁，适用于单线程的情况，在不存在锁竞争的时候进入同步方法/代码块则使用偏向锁
 *         MarkWord 存储的是偏向的线程id
 * 6.轻量锁，适用于竞争比较不激烈的情况，存在竞争时升级为轻量锁，采用自旋锁，如果同步方法/代码块执行时间很短的话，采用轻量锁
 *   虽然会占用CPU资源，但是相对比重量锁还是高效， MarkWord存储的是指向线程中Lock Record的指针
 * 7.重量锁，适用竞争激烈的情况， 如果同步方法/代码块执行时间长，那么使用轻量锁带来的性能消耗就更严重，这时候升级为重量锁
 *   MarkWord存储的是指向堆中的monitor对象的指针
 */
public class SynchronizedTest {

    public static class Phone {

        private static Object lock = new Object();

        public synchronized void sendMsg() {
            System.out.println("--send msg");
        }

        public static synchronized void sendEmail() {
            System.out.println("--send email");
        }

        public void sendHello() {
            System.out.println("进入方法了");
            synchronized (this) {
                System.out.println("--send hello");
            }
        }

        public void sendWorld() {
            synchronized (lock) {
                System.out.println("--send world");
            }
        }
    }

    public static void main(String[] args) {
//        Phone phone1 = new Phone();
//        Phone phone2 = new Phone();
//
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Phone.sendEmail();
//        }).start();
//
//        new Thread(() -> {
//            Phone.sendEmail();
//        }).start();
//


        biasLockTest();


    }


    /**
     * 偏向锁， jdk默认开启偏向锁，默认会延迟4s才会配置偏向锁, 可以通过指令关闭延迟
     *
     * 在jdk15之后，偏向锁默认关闭， 标志位101
     */
    private static void biasLockTest() {

        //没有关闭延迟的话，默认等待5s,来获取偏向锁
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Object object = new Object();

        //如果在加锁之前执行过hashcode ，是不会进入偏向锁的，因为markword的记录了hashcode, 不能记录当前线程id
        //如果加偏向锁之后，执行hashcode, 会升级为轻量锁
        //hashcode 是一个随机的， 对象执行hashcode之后会记录这个hashcode, 所以之后的每次获取都是一致的，
        //如果不是读取对象头中的hashcode, 而是重新获取的话会得到不一致的hashcode
//        System.out.println(object.hashCode());

        synchronized (object) {

//            System.out.println(object.hashCode());
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }

    }

    /**
     * 轻量级锁是为了线程近乎交替执行同步块时提高性能， 最多有一个线程来竞争锁
     *
     * 竞争成功，当前线程获得锁，失败，则会通过cas 自旋来不断获得锁
     *
     * 标志位 000
     *
     * 自旋达到一定次数依然没有成功，那么锁会升级为重量锁
     *
     * jdk7，自适应自旋锁， 如果获取了自旋锁，那么下次自旋的次数上限会增加，也就是它自旋的次数是根据当前的状态来不断变化的
     */
    private static void qTest() {

        Object object = new Object();

        synchronized (object) {
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }

    }


    /**
     * synchronized 重量级锁，是基于进入和退出Monitor对象实现的，在编译的时候会将同步块的开始位置插入monitor enter指令，
     * 在结束位置插入monitor exit指令
     *
     * 当线程执行到monitor enter指令时，会尝试获取对象所对应的Monitor所有权， 如果获取到了，会在monitor的owner中存放
     * 当前线程的id,这样他将处于锁定状态，除非退出同步块，否则其他线程将无法获取到这个monitor
     *
     * 标志位010
     */
    private static void zTest() {
        Object lock = new Object();

        new Thread(() -> {

            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }, "t1").start();


        new Thread(() -> {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }, "t2").start();

    }
}
