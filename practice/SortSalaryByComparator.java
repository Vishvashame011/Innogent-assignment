package practice;

import java.util.ArrayList;
import java.util.List;

class Empoyee  {
    int id;
    String name;
    double salary;

    public Empoyee(int id, String name, double salary) {
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
    public String toString() {
        return "Empoyee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class SortSalaryByComparator {
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
    }
}
