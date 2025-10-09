package multithreading_assignment5.semaphoreExample;

import java.util.concurrent.Semaphore;

class ParkingSlot{
    private final Semaphore semaphore;

    ParkingSlot(int slots) {
        semaphore = new Semaphore(slots); // total parking slots
    }

    public void parkCar(String carName){
        try{
            System.out.println(carName + " is trying to park car1");
            semaphore.acquire();
            System.out.println(carName + " is parked!");
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            System.out.println(carName + " is leaving from parking!");
            semaphore.release();
        }
    }


}
public class SemaphoreExample {
    public static void main(String[] args) {
        ParkingSlot parkingSlot = new ParkingSlot(3);

        for(int i=1; i<=5; i++){
            String carName = "Car : " + i;
            new Thread(() -> parkingSlot.parkCar(carName)).start();
        }
    }
}
