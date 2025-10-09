package multithreading_assignment5.executorService;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ExecutorService {

    static class LineCountTask implements Callable<Integer> {
        private final File file;

        public LineCountTask(File file) {
            this.file = file;
        }

        @Override
        public Integer call() {
            int lineCount = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while (reader.readLine() != null) {
                    lineCount++;
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " processed " + file.getName() + " → " + lineCount + " lines");
            return lineCount;
        }
    }

    public static void main(String[] args) {
        File folder = new File("D:\\Vishvash-Internship\\Java-practice\\");

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory path!");
            return;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No text files found in directory!");
            return;
        }

        int numThreads = Math.min(files.length, Runtime.getRuntime().availableProcessors());
        java.util.concurrent.ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        List<Future<Integer>> futures = new ArrayList<>();

        try {
            for (File file : files) {
                LineCountTask task = new LineCountTask(file);
                Future<Integer> future = executor.submit(task);
                futures.add(future);
            }

            int totalLines = 0;
            for (Future<Integer> f : futures) {
                try {
                    totalLines += f.get();
                } catch (ExecutionException e) {
                    System.err.println("Error processing a file: " + e.getMessage());
                }
            }

            System.out.println("\nTotal number of lines across all files: " + totalLines);

        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
