package com.zijin.core.juc;

import java.util.concurrent.*;

public class CompletableFutureTest {


    /**
     * 有无返回值
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void test1()  {

        //无返回值
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("f1:" + Thread.currentThread().getName());

        });
        //有返回值
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("f2:" + Thread.currentThread().getName());
            return "hello supplyAsync";
        });
        //编译时异常校验，抛异常
        try {
            System.out.println(f2.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        //编译不抛异常
        System.out.println(f2.join());
    }

    /**
     * lambda, 区别thenRun() 与 thenRunAsync, 在前面的线程指定了线程池之后， 执行thenRunAsync会使用默认的forkJoin线程池
     * 任务分解，前一个任务处理结果进入下一个处理任务
     */
    public static void test2() {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,10, 1000,TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName());
        }, threadPoolExecutor).thenAccept((v) -> {
                    System.out.println(Thread.currentThread().getName());
                })
                .thenRunAsync(() -> {
                    System.out.println(Thread.currentThread().getName());
                })
          .whenComplete((k,v) -> {
                    System.out.println(Thread.currentThread().getName());
                });

        threadPoolExecutor.shutdown();

    }


    /**
     * forkJoin线程池
     */
    public static void test3() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
    }





    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1();
        test2();
    }


}
