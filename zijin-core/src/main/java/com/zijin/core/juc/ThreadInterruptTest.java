package com.zijin.core.juc;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程中断， 一个线程并不建议由其他线程直接关闭，而是通过中断协商机制来实现线程的停止
 */
public class ThreadInterruptTest {

    private static volatile boolean isInterrupt = false;

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

//        atomicTest();

//        threadInterruptTest();

//        blockThreadInterruptTest();

//        staticInterruptTest();

//        threadStopTest();

        threadJoinTest();
    }









    /**
     * thread join 线程1加入到thread2, 阻塞thread的运行， thread1执行完成，在执行thread2
     */
    private static void threadJoinTest() {
        Thread thread = new Thread(() -> {
            int i = 0;
            while (i <100) {
                System.out.println("当前线程：" + Thread.currentThread().getName());

                i++;
            }
            System.out.println("当前线程：" + Thread.currentThread().getName() + " 执行完成");

        });
        thread.setName("thread1");

        Thread thread2 = new Thread(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getName());

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("当前线程：" + Thread.currentThread().getName() + " 执行完毕");

        });


        thread2.setName("thread2");
        thread2.start();
        thread.start();

        thread.interrupt();
    }


    /**
     * jdk已经不建议直接使用stop终止线程
     */
    private static void threadStopTest() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("thread1 is running");
            }
        });
        thread1.start();

        try {
            TimeUnit.SECONDS.sleep(5);
            thread1.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Thread 静态方法interrupted() 会获取中断标志位之后重置标志位， 而实例方法isInterrupted() 不会重置标志位
     */
    private static void staticInterruptTest() {
        System.out.println(Thread.currentThread().getName() + ":" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + ":" + Thread.interrupted());

        System.out.println("+++");
        Thread.currentThread().interrupt();
        System.out.println("---");
        System.out.println(Thread.currentThread().getName() + ":" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + ":" + Thread.interrupted());
    }


    /**
     * 被阻塞的线程，进行中断, sleep, wait,join 等线程被阻塞， 线程中断状态会被清除，并且抛出中断异常
     *
     * 但是如果
     */
    private static void blockThreadInterruptTest() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("thread1 线程中断");
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("thread1 中断标志：" + Thread.currentThread().isInterrupted());
                    e.printStackTrace();  //这里会抛出中断异常，并清除中断状态，如果异常没有往上层抛，外层循环就不会中断，
                    Thread.currentThread().interrupt(); //可以通过再次中断标志
                }
                System.out.println("thread1 running-------");
            }
        });
        thread1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("设置thread1 中断");
            thread1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * 利用Thread的interrupt标志位进行协商中断, 调用Thread.interrupt()不会立即停止线程，只是改变标志位
     */
    private static void threadInterruptTest() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("thread1 中断");
                    break;
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("thread2 set thread1 interrupt");
                thread1.interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread2.start();
    }


    /**
     * 利用原子类设置中断标志，线程自己配合进行中断
     */
    private static void atomicTest() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (atomicBoolean.get()) {
                    System.out.println("thread1 中断");
                    break;
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("thread2 set thread1 interrupt");
                atomicBoolean.set(true);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread2.start();
    }



    /**
     * 通过利用volatile 的可见性， 线程自己判断进行中断
     */
    private static void volatileTest() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (isInterrupt) {
                    System.out.println("thread1 中断");
                    break;
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("thread2 set thread1 interrupt");
                isInterrupt = true;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread2.start();
    }





}
