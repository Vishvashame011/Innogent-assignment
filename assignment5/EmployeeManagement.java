package assignment5;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class Employee{
    private int empId;
    private String empName;
    private double salary;
    private String dept;

    // parameterized constructor
    public Employee(int empId, String empName, double salary, String dept) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
        this.dept = dept;
    }

    // getters methods
    public int getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public double getSalary() {
        return salary;
    }

    public String getDept() {
        return dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", salary=" + salary +
                ", dept='" + dept + '\'' +
                '}';
    }
}
public class EmployeeManagement {
    public static void main(String[] args) {
        List<Employee> list = Arrays.asList(
                new Employee(1, "Vishvash", 20000, "IT"),
                new Employee(2, "Shrishti", 25000, "HR"),
                new Employee(3, "Mahak", 50000, "IT"),
                new Employee(4, "Pradeep", 18000, "Finance"),
                new Employee(5, "Anushka", 45000, "IT")
        );

        // Original List data printing
        System.out.println("---------------------- All Employees list ----------------------");
        Iterator itr = list.iterator();
        while(itr.hasNext()){
            System.out.println(itr.next());
        }

        // filter employee by dep using streams
        List<Employee> filterByDep = list.stream()
                .filter(emp -> emp.getDept().equalsIgnoreCase("IT"))
                .collect(Collectors.toList());

        System.out.println("Filter by Dep : " + filterByDep);

        // compute total salary
        double totalSalary = list.stream().
                map(Employee::getSalary).
                reduce(0.0, (sum, salary) -> sum + salary);
        System.out.println("Total salary is : " + totalSalary);

        // convert emp named to uppercase
        list.stream()
                .map(Employee::getEmpName)
                .map(String::toUpperCase)
                .forEach(System.out::println);




    }
}
