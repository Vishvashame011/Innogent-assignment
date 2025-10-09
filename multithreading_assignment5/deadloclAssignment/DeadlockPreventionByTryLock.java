package multithreading_assignment5.deadloclAssignment;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

public class DeadlockPreventionByTryLock {
    private final ReentrantLock lock1 = new ReentrantLock();
    private final ReentrantLock lock2 = new ReentrantLock();

    public void taskA() {
        try {
            if (lock1.tryLock(1, TimeUnit.SECONDS)) {

                System.out.println(Thread.currentThread().getName() + " locked lock1");
                Thread.sleep(100);

                if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " locked lock2");
                    lock2.unlock();
                } else {
                    System.out.println(Thread.currentThread().getName() + " couldn’t get lock2, releasing lock1");
                }
                lock1.unlock();
            } else {
                System.out.println(Thread.currentThread().getName() + " couldn’t get lock1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void taskB() {
        try {
            if (lock2.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " locked lock2");
                Thread.sleep(100);

                if (lock1.tryLock(1, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + " locked lock1");
                    lock1.unlock();
                } else {
                    System.out.println(Thread.currentThread().getName() + " couldn’t get lock1, releasing lock2");
                }
                lock2.unlock();
            } else {
                System.out.println(Thread.currentThread().getName() + " couldn’t get lock2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DeadlockPreventionByTryLock deadlockPreventionByTryLock = new DeadlockPreventionByTryLock();

        Thread t1 = new Thread(deadlockPreventionByTryLock::taskA, "Thread-1");
        Thread t2 = new Thread(deadlockPreventionByTryLock::taskB, "Thread-2");

        t1.start();
        t2.start();
    }
}

