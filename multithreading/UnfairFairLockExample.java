package multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class UnfairFairLockExample {
    private static final ReentrantLock lock = new ReentrantLock(true); // false = unfair lock, true = fair lock

    public static void main(String[] args) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
//            for (int i = 0; i < 3; i++) {
                lock.lock();
                try {
                    System.out.println(threadName + " acquired the lock");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                } finally {
                    System.out.println(threadName + " released the lock");
                    lock.unlock();
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
//            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

