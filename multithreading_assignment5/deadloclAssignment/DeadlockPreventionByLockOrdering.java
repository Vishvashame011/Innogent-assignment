package multithreading_assignment5.deadloclAssignment;

class Resources {
    String name;

    Resources(String name) {
        this.name = name;
    }
}

public class DeadlockPreventionByLockOrdering {
    private final Resources resource1 = new Resources("Resource-1");
    private final Resources resource2 = new Resources("Resource-2");

    public void methodA() {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName() + " locked " + resource1.name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource2.name);
            }
        }
    }

    public void methodB() {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName() + " locked " + resource1.name);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource2.name);
            }
        }
    }

    public static void main(String[] args) {
        DeadlockPreventionByLockOrdering deadlockPreventionByLockOrdering = new DeadlockPreventionByLockOrdering();

        Thread t1 = new Thread(deadlockPreventionByLockOrdering::methodA, "Thread-1");
        Thread t2 = new Thread(deadlockPreventionByLockOrdering::methodB, "Thread-2");

        t1.start();
        t2.start();
    }
}

