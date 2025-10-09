package multithreading_assignment5.parellelVsExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelVsExecutorExample {

    private static int[] generateArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000);
        }
        return arr;
    }

    private static long sumOfSquaresUsingParallelStream(int[] arr) {
        long start = System.currentTimeMillis();

        long sum = Arrays.stream(arr)
                .parallel()
                .map(x -> x * x)
                .asLongStream()
                .sum();

        long end = System.currentTimeMillis();
        System.out.println("Parallel Stream Time: " + (end - start) + " ms");
        return sum;
    }

    private static long sumOfSquaresUsingExecutorService(int[] arr, int numThreads)
            throws InterruptedException, ExecutionException {

        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Long>> futures = new ArrayList<>();

        int chunkSize = arr.length / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int startIdx = i * chunkSize;
            int endIdx = (i == numThreads - 1) ? arr.length : (i + 1) * chunkSize;

            Callable<Long> task = () -> {
                long localSum = 0;
                for (int j = startIdx; j < endIdx; j++) {
                    localSum += (long) arr[j] * arr[j];
                }
                return localSum;
            };

            futures.add(executor.submit(task));
        }

        long totalSum = 0;
        for (Future<Long> f : futures) {
            totalSum += f.get();
        }

        executor.shutdown();

        long end = System.currentTimeMillis();
        System.out.println("ExecutorService Time: " + (end - start) + " ms");

        return totalSum;
    }

    public static void main(String[] args) throws Exception {
        int size = 5_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        int[] arr = generateArray(size);
        System.out.println("Available Cores: " + numThreads);

        long sum1 = sumOfSquaresUsingParallelStream(arr);
        long sum2 = sumOfSquaresUsingExecutorService(arr, numThreads);

        System.out.println("\nParallel Stream Result: " + sum1);
        System.out.println("ExecutorService Result: " + sum2);
    }
}
