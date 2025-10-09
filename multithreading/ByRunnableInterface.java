package multithreading;

class MyThread2 implements Runnable{

    @Override
    public void run() {
        for (int i=1; i<=10; i++){
            System.out.println(Thread.currentThread().getName() + " : " +  i);
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}
public class ByRunnableInterface {
    public static void main(String[] args) {
        MyThread2 thread2 = new MyThread2();
        Thread thread = new Thread(thread2);

        thread.start();

        for (int i=1; i<=10; i++){
            System.out.println(Thread.currentThread().getName() + " : " +  i);
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

    }
}
