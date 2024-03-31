package com.zijin.core.juc;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁与非公平锁， ReentrantLock与 synchronized都可以是非公平锁
 * 公平锁： 按照获取锁的顺序获取锁， 防止有的线程空转，
 * 非公平锁： 不按获取锁的顺序获取锁，有可能最后一个获取锁的线程获取到了当前所， 并发性能更好，减少了线程切换的开销
 *
 */
public class FairLockTest {

    private ReentrantLock lock = new ReentrantLock(); //非公平锁
//    private ReentrantLock lock = new ReentrantLock(true); //公平锁












}
