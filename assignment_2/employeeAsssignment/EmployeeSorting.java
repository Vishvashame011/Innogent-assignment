package assignment_2.employeeAsssignment;


import java.util.*;

class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public int compareTo(Employee other) {
        // Natural ordering: by ID
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                '}';
    }

}

class EmployeeComparators {

    // Employee sorting by their dept -> name -> salary in ascending
    public static final Comparator<Employee> byDepNameSalary =
            Comparator.comparing(Employee::getDepartment)
                    .thenComparing(Employee::getName)
                    .thenComparingDouble(Employee::getSalary);

    // Salary descending
    public static final Comparator<Employee> bySalaryDesc =
            Comparator.comparingDouble(Employee::getSalary).reversed();
}
public class EmployeeSorting {

    private static void printList(List<Employee> list) {
        Iterator<Employee> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(3, "Vishvash", "IT", 50000));
        employees.add(new Employee(1, "Shrishti", "Finance", 70000));
        employees.add(new Employee(4, "Mahak", "IT", 60000));
        employees.add(new Employee(2, "Harsh", "Editor", 55000));
        employees.add(new Employee(5, "Pradeep", "Accountant", 65000));

        // print the original list
        System.out.println("Original List:");
        printList(employees);

        // Employee sorting by their dept -> name -> salary in ascending
        Collections.sort(employees, EmployeeComparators.byDepNameSalary);
        System.out.println("\nSorted by Department → Name → Salary:");
        printList(employees);

        // Sort Salary (descending)
        Collections.sort(employees, EmployeeComparators.bySalaryDesc);
        System.out.println("\nSorted by Salary (Descending):");
        printList(employees);

        // Natural order (by ID) - uses Comparable
        Collections.sort(employees);
        System.out.println("\nSorted by ID (Comparable - Natural Order):");
        printList(employees);
    }
}
