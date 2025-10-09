package multithreading;

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(100); // slight pause for better interleaving
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

public class ByThreadClass {
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.setName("ChildThread"); // optional: naming the thread
        thread.start();

        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
