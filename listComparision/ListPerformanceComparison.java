package listComparision;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPerformanceComparison {

    // calculate insertion time
    private static long calculateInsertionTime(List<Integer> list, int size) {
        long startingTime = System.currentTimeMillis();
        for(int i=0; i<size; i++){
            list.add(i);
        }

        long endingTime = System.currentTimeMillis();
        return endingTime - startingTime;
    }

    private static long calculateDeletionTime(List<Integer> list){
        long startingTime = System.currentTimeMillis();

        while(!list.isEmpty()){
            list.remove(0);
        }

        long endingTime = System.currentTimeMillis();
        return endingTime - startingTime;

    }
    public static void main(String[] args) {
        int[] sizes = {10_000, 50_000, 1_00_000};

        for(int size : sizes){
            System.out.println("Comparision for size : " + size);

            // for array list
            List<Integer> arrayList = new ArrayList<>();

            long arrayInsertionTime = calculateInsertionTime(arrayList, size);
            long arrayDeletionTime = calculateDeletionTime(arrayList);



            System.out.println("ArrayList - Insertion Time: " + arrayInsertionTime + " ms");
            System.out.println("ArrayList - Deletion Time: " + arrayDeletionTime + " ms");

            // for linked list
            List<Integer> linkedList = new LinkedList<>();

            long linkedListInsertionTime = calculateInsertionTime(linkedList, size);
            long linkedListDeletionTime = calculateDeletionTime(linkedList);

            System.out.println("Linked List - Insertion Time: " + linkedListInsertionTime + " ms");
            System.out.println("Linked List - Deletion Time: " + linkedListDeletionTime + " ms");
        }
    }
}
