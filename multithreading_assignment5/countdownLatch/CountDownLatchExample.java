package multithreading_assignment5.countdownLatch;

import java.util.concurrent.CountDownLatch;

class Worker extends Thread {
    private CountDownLatch latch;
    private String taskName;

    public Worker(CountDownLatch latch, String taskName) {
        this.latch = latch;
        this.taskName = taskName;
    }

    @Override
    public void run() {
        try {
            System.out.println(taskName + " started...");
            Thread.sleep(1000); // Simulate task (e.g. loading config, DB connection)
            System.out.println(taskName + " completed!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.countDown(); // Reduce the latch count by 1
        }
    }
}

public class CountDownLatchExample {
    public static void main(String[] args) {
        int numberOfTasks = 3;
        CountDownLatch latch = new CountDownLatch(numberOfTasks);

        System.out.println("Main thread: Starting initialization...");

        new Worker(latch, "Load Configuration").start();
        new Worker(latch, "Connect to Database").start();
        new Worker(latch, "Initialize Cache").start();

        try {
            latch.await(); // Main thread waits here until all workers are done
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All initialization tasks are complete. Main thread continues...");
    }
}

