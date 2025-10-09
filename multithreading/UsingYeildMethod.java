package multithreading;

class MyThread4 extends Thread{
    @Override
    public void run(){
        for(int i=1; i<=5; i++){
            System.out.println(Thread.currentThread().getName() + " running....");
            Thread.yield();
        }
    }
}
public class UsingYeildMethod {
    public static void main(String[] args) {
        MyThread4 thread4 = new MyThread4();
        MyThread4 thread41 = new MyThread4();
        thread4.start();
        thread41.start();
    }
}
