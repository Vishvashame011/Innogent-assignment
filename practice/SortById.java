package practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class Employee implements Comparable<Employee>{

    int id;
    String name;
    double salary;

    public Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }


    @Override
    public int compareTo(Employee e) {
        return Integer.compare(this.id, e.id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
public class SortById {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();

        list.add(new Employee(2, "Vishvash Ame", 21000.00));
        list.add(new Employee(3, "Shrishti Jain", 105000.00));
        list.add(new Employee(1, "Anushka", 35000.00));
        list.add(new Employee(4, "Harsh Soni", 65000.00));
        list.add(new Employee(5, "Sawan", 44000.00));

        System.out.println("Original list");
        for(Employee e : list){
            System.out.println(e);
        }

        System.out.println();

        Collections.sort(list);

        System.out.println("modified list");
        Iterator<Employee> it = list.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
