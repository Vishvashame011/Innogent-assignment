package multithreading;

class MyThread3 extends Thread{
    @Override
    public void run() {
        try{
            Thread.sleep(100);
            System.out.println("Thread is running.....");
        }
        catch (InterruptedException e){
            System.out.println("Thread Interrupted : " + e.getMessage());
        }
    }
}
public class ByInterruptMethod {
    public static void main(String[] args) {
        MyThread3 thread3 = new MyThread3();
        thread3.start();
        thread3.interrupt();
    }
}
