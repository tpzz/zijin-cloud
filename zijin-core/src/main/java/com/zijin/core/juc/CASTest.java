package com.zijin.core.juc;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * CAS (compare and swap) 比较并交换，是jdk提供的非阻塞原子性操作，他是通过硬件保证了比较更新的原子性，
 *
 *
 * CAS是CPU的原子指令（cmpxchg） ,执行该指令的时候，会判断当前系统是否为多核系统，如果是就给总线加锁，只有一个线程会对总线加锁成功
 * ，加锁成功后进行cas操作，也就是说cas的原子性实际上是cpu实现独占的，比起synchronized重量级锁，这里的排他时间要短，所以在多线程
 * 情况下性能会比较好
 */
public class CASTest {

    /**
     * 利用CAS实现自旋锁
     */
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 当前线程为空的时候 获取锁，
     *
     * 这个获取锁的过程不是阻塞的，通过CAS 一直在比较，如果为null则获得锁
     *
     * 优点：避免了synchronized重量锁， 但是带来了cpu空转，消耗性能
     *
     * 1.循环消耗大
     * 2.ABA问题，  通过版本控制，来分析中间值变化的过程
     */
    public void lock() {
       Thread thread = Thread.currentThread();
       while (!atomicReference.compareAndSet(null, thread)) {

       }
    }

    /**
     * 释放锁，上面的循环则退出， 供其他线程获得锁
     */
    public void unlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }




    public static void main(String[] args) {

//        atomicIntegerTest();

//        ABALockTest();

        //计数， 计数为0的时候唤醒
        CountDownLatch countDownLatch = new CountDownLatch(10);


        for (int i=0;i<10;i++) {
            try {
                System.out.println("");
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("以上线程都结束");
    }


    /**
     * 带版本号的原子类引用，
     */
    private static void ABALockTest() {
        User z3 = new User(1,"z3");
        User l4 = new User(2, "l4");
        AtomicStampedReference<User> atomicStampedReference = new AtomicStampedReference<>(z3, 1);

        System.out.println(atomicStampedReference.getReference());

        atomicStampedReference.compareAndSet(z3, l4, 1, 2);

        System.out.println(atomicStampedReference.getReference());

        atomicStampedReference.compareAndSet(z3, l4, 1, 2);

        System.out.println(atomicStampedReference.getReference());

    }



    private static class User {
        private Integer age;

        private String name;

        public User(Integer age, String name) {
            this.age = age;
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    /**
     * 自旋锁
     */
    private static void spinLockTest() {
        CASTest casTest = new CASTest();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " --come in");
            casTest.lock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + " unlock");
            casTest.unlock();

        }, "t1").start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "--come in");
            casTest.lock();
            System.out.println(Thread.currentThread().getName() + " --unlock");
            casTest.unlock();
        }, "t2").start();



    }




    /**
     * atomicInteger compareAndSet 会比较期望值， 期望值符合才会进行修改
     */
    private static void atomicIntegerTest() {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 100) + " value:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 111) + " value:" + atomicInteger.get());

        atomicInteger.getAndIncrement();

        System.out.println(atomicInteger.get());
    }










}
