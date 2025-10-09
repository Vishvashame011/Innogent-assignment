package multithreading_assignment5.deadloclAssignment;

class Resource{
    String name;

    public Resource(String name){
        this.name = name;
    }
}
public class DeadlockOccur {

    private final Resource rs1 = new Resource("resource-1");
    private final Resource rs2 = new Resource("resource-2");

    public void method(){
        synchronized (rs1){
            System.out.println(Thread.currentThread().getName() + " locked " + rs1.name);
            try{
                Thread.sleep(100);
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            synchronized (rs2){
                System.out.println(Thread.currentThread().getName() + " locked " + rs2.name);
            }
        }
    }

    public void methodB() {
        synchronized (rs2) {
            System.out.println(Thread.currentThread().getName() + " locked " + rs2.name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            synchronized (rs1) {
                System.out.println(Thread.currentThread().getName() + " locked " + rs1.name);
            }
        }
    }
    public static void main(String[] args) {
        DeadlockOccur deadlockOccur = new DeadlockOccur();

        Thread t1 = new Thread(deadlockOccur::method, "Thread-1");
        Thread t2 = new Thread(deadlockOccur::methodB, "Thread-2");

        t1.start();
        t2.start();
    }
}
