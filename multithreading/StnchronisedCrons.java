package multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BankAccount{
    private int balance = 100;

    private final Lock lock = new ReentrantLock();


    public void withdraw(int amount) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " attempting to withdraw " + amount);
        try{
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)){
                if(balance >= amount){
                    try{
                        System.out.println(Thread.currentThread().getName() + " proceeding with withdraw");;
                        balance -= amount;
                        Thread.sleep(3000);

                        System.out.println(Thread.currentThread().getName() + " completed withdrawal, Remaining balance is : " + balance);
                    }
                    catch (Exception e){
                        System.out.println(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                    finally {
                        lock.unlock();
                    }
                }
                else{
                    System.out.println(Thread.currentThread().getName() + " insufficient balance");
                }
            }
            else{
                System.out.println(Thread.currentThread().getName() + " could not acquire the lock, will try later");
            }
        }
        catch (Exception e){
            Thread.currentThread().interrupt();
        }

        if(Thread.currentThread().isInterrupted()){
            System.out.println("Thread is interrupted");
        }
    }

}
public class StnchronisedCrons {
    public static void main(String[] args) {
        BankAccount bob = new BankAccount();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    bob.withdraw(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t1 = new Thread(task, "Thread 1");
        Thread t2 = new Thread(task, "Thread 2");

        t1.start();
        t2.start();
    }
}
