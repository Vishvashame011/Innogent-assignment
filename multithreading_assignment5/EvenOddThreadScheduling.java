package multithreading_assignment5;

class NumberPrinter {
    private int number = 1;

    public synchronized void printOdd() {
        while (true) {
            if (number > 30) return;
            while (number % 2 == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (number > 30) return;
            System.out.println(Thread.currentThread().getName() + " : " + number);
            number++;
            notify(); // tell to even thread
        }
    }

    public synchronized void printEven() {
        while (true) {
            if (number > 30) return;
            while (number % 2 == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (number > 30) return;
            System.out.println(Thread.currentThread().getName() + " : " + number);
            number++;
            notify(); // tell to odd thread
        }
    }
}

public class EvenOddThreadScheduling {
    public static void main(String[] args) throws InterruptedException {
        NumberPrinter printer = new NumberPrinter();

        Thread oddThread = new Thread(() -> printer.printOdd());

        oddThread.setName("OddThread");

        Thread evenThread = new Thread(() -> printer.printEven());

        evenThread.setName("EvenThread");

        oddThread.start();
        evenThread.start();
    }
}
