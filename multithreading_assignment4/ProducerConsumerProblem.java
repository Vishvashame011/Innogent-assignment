package multithreading_assignment4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class SharedResource {
    private final Queue<Integer> bucket = new LinkedList<>();
    private final int capacity;

    public SharedResource(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(int count) {
        try {
            while (bucket.size() == capacity) {
                System.out.println("Bucket is full -> Producer waiting!!");
                wait();
            }

            int itemsToAdd = Math.min(count, capacity - bucket.size());
            for (int i = 0; i < itemsToAdd; i++) {
                int product = new Random().nextInt(100);
                bucket.add(product);
                System.out.println(Thread.currentThread().getName() + " produced: " + product);
            }

            System.out.println("Current bucket size after producing: " + bucket.size());
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted!");
        }
    }

    // Consumer removes items from bucket
    public synchronized void consume(int count) {
        try {
            while (bucket.isEmpty()) {
                System.out.println("Bucket is empty -> Consumer waiting!");
                wait();
            }

            int itemsToConsume = Math.min(count, bucket.size());
            for (int i = 0; i < itemsToConsume; i++) {
                int product = bucket.poll();
                System.out.println(Thread.currentThread().getName() + " consumed: " + product);
            }

            System.out.println("Current bucket size after consuming: " + bucket.size());
            notifyAll(); // notify producer to produce again
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer interrupted!");
        }
    }
}

// Producer thread
class Producer extends Thread {
    private final SharedResource sharedResource;
    private final int iterations;

    public Producer(SharedResource sharedResource, int iterations, String name) {
        super(name);
        this.sharedResource = sharedResource;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 1; i <= iterations; i++) {
            int items = 1 + random.nextInt(5);
            sharedResource.produce(items);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer extends Thread {
    private final SharedResource sharedResource;
    private final int iterations;

    public Consumer(SharedResource sharedResource, int iterations, String name) {
        super(name);
        this.sharedResource = sharedResource;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 1; i <= iterations; i++) {
            int items = 1 + random.nextInt(5);
            sharedResource.consume(items);

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ProducerConsumerProblem {
    public static void main(String[] args) {

        try{
        // set bucket size
        int bucketCapacity = 10;

        SharedResource sharedResource = new SharedResource(bucketCapacity);

        Producer producer = new Producer(sharedResource, 6, "ProducerThread");
        Consumer consumer = new Consumer(sharedResource, 6, "ConsumerThread");

        producer.start();
        consumer.start();


            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}

