package com.zijin.core.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyTask());
        Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }



    public static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "hello world";
        }
    }
}
