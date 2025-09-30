package assignment3;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentManagementSystem {

    private List<ClassRoom> classList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();
    private List<Address> addressList = new ArrayList<>();

    // Add a class
    public void addClassRoom(ClassRoom c) {
        classList.add(c);
    }

    // Add a student with age validation
    public void addStudent(Student s) {
        if (s.getAge() > 20) {
            System.out.println("Cannot add student " + s.getName() + " - Age exceeds limit!");
            return;
        }
        studentList.add(s);
    }

    // Add address
    public void addAddress(Address a) {
        addressList.add(a);
    }

    // Assign ranks based on marks
    public void assignRanks() {
        List<Student> sorted = new ArrayList<>(studentList);
        sorted.sort((s1, s2) -> s2.getMarks() - s1.getMarks());

        int prevMarks = -1;
        int rank = 0;
        int count = 0;

        for (Student s : sorted) {
            if (s.getMarks() != prevMarks) {
                rank = ++count;  // new rank only when marks differ
            }
            s.setRank(rank);
            prevMarks = s.getMarks();
        }
    }


    // Helper to get class name from classId
    private String getClassName(int classId) {
        return classList.stream()
                .filter(c -> c.getId() == classId)
                .map(ClassRoom::getName)
                .findFirst().orElse("Unknown");
    }

    // Helper to get addresses of a student
    private List<Address> getAddresses(int studentId) {
        return addressList.stream()
                .filter(a -> a.getStudentId() == studentId)
                .collect(Collectors.toList());
    }

    // Filter students by pincode with optional filters
    public List<Student> findStudentsByPincode(String pincode, Gender gender, Integer age, String className) {
        // get studentIds from addresses
        Set<Integer> studentIds = addressList.stream()
                .filter(a -> a.getPincode().equals(pincode))
                .map(Address::getStudentId)
                .collect(Collectors.toSet());

        return studentList.stream()
                .filter(s -> studentIds.contains(s.getId()))
                .filter(s -> gender == null || s.getGender() == gender)
                .filter(s -> age == null || s.getAge() == age)
                .filter(s -> className == null || getClassName(s.getClassId()).equalsIgnoreCase(className))
                .collect(Collectors.toList());
    }

    // Filter students by city with optional filters
    public List<Student> findStudentsByCity(String city, Gender gender, Integer age, String className) {
        Set<Integer> studentIds = addressList.stream()
                .filter(a -> a.getCity().equalsIgnoreCase(city))
                .map(Address::getStudentId)
                .collect(Collectors.toSet());

        return studentList.stream()
                .filter(s -> studentIds.contains(s.getId()))
                .filter(s -> gender == null || s.getGender() == gender)
                .filter(s -> age == null || s.getAge() == age)
                .filter(s -> className == null || getClassName(s.getClassId()).equalsIgnoreCase(className))
                .collect(Collectors.toList());
    }

    // Filter students by class with optional filters
    public List<Student> findStudentsByClass(String className, Gender gender, Integer age, String city, String pincode) {
        int classId = classList.stream()
                .filter(c -> c.getName().equalsIgnoreCase(className))
                .map(ClassRoom::getId)
                .findFirst().orElse(-1);

        Set<Integer> studentIdsCity;
        Set<Integer> studentIdsPin;

        if (city != null) {
            studentIdsCity = addressList.stream()
                    .filter(a -> a.getCity().equalsIgnoreCase(city))
                    .map(Address::getStudentId)
                    .collect(Collectors.toSet());
        } else {
            studentIdsCity = null;
        }
        if (pincode != null) {
            studentIdsPin = addressList.stream()
                    .filter(a -> a.getPincode().equals(pincode))
                    .map(Address::getStudentId)
                    .collect(Collectors.toSet());
        } else {
            studentIdsPin = null;
        }

        return studentList.stream()
                .filter(s -> s.getClassId() == classId)
                .filter(s -> gender == null || s.getGender() == gender)
                .filter(s -> age == null || s.getAge() == age)
                .filter(s -> city == null || studentIdsCity.contains(s.getId()))
                .filter(s -> pincode == null || studentIdsPin.contains(s.getId()))
                .collect(Collectors.toList());
    }

    // Passed students
    public List<Student> getPassedStudents(Gender gender, Integer age, String className, String city, String pincode) {
        return studentList.stream()
                .filter(s -> s.getMarks() >= 50)
                .filter(s -> gender == null || s.getGender() == gender)
                .filter(s -> age == null || s.getAge() == age)
                .filter(s -> className == null || getClassName(s.getClassId()).equalsIgnoreCase(className))
                .filter(s -> city == null || getAddresses(s.getId()).stream().anyMatch(a -> a.getCity().equalsIgnoreCase(city)))
                .filter(s -> pincode == null || getAddresses(s.getId()).stream().anyMatch(a -> a.getPincode().equals(pincode)))
                .collect(Collectors.toList());
    }

    // Failed students
    public List<Student> getFailedStudents(Gender gender, Integer age, String className, String city, String pincode) {
        return studentList.stream()
                .filter(s -> s.getMarks() < 50)
                .filter(s -> gender == null || s.getGender() == gender)
                .filter(s -> age == null || s.getAge() == age)
                .filter(s -> className == null || getClassName(s.getClassId()).equalsIgnoreCase(className))
                .filter(s -> city == null || getAddresses(s.getId()).stream().anyMatch(a -> a.getCity().equalsIgnoreCase(city)))
                .filter(s -> pincode == null || getAddresses(s.getId()).stream().anyMatch(a -> a.getPincode().equals(pincode)))
                .collect(Collectors.toList());
    }

    // Delete student
    public void deleteStudent(int studentId) {
        Optional<Student> studentOpt = studentList.stream().filter(s -> s.getId() == studentId).findFirst();
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            int classId = student.getClassId();
            studentList.remove(student);

            // remove addresses
            addressList.removeIf(a -> a.getStudentId() == studentId);

            // if no students left in that class, remove the class
            boolean classEmpty = studentList.stream().noneMatch(s -> s.getClassId() == classId);
            if (classEmpty) {
                classList.removeIf(c -> c.getId() == classId);
                System.out.println("Removed classId=" + classId + " because no students left.");
            }

            System.out.println("Deleted student id=" + studentId);
        } else {
            System.out.println("Student id=" + studentId + " not found.");
        }
    }


    // pagination filters
    public List<Student> filterStudents(Gender gender, int fromIndex, int toIndex, String orderBy) {
        // filter
        Stream<Student> stream = studentList.stream();
        if (gender != null) {
            stream = stream.filter(s -> s.getGender() == gender);
        }

        // sorting
        if ("name".equalsIgnoreCase(orderBy)) {
            stream = stream.sorted(Comparator.comparing(Student::getName));
        } else if ("marks".equalsIgnoreCase(orderBy)) {
            stream = stream.sorted(Comparator.comparingInt(Student::getMarks).reversed());
        } else {
            stream = stream.sorted(Comparator.comparingInt(Student::getId)); // default
        }

        // collecting after sorting
        List<Student> sorted = stream.collect(Collectors.toList());

        // pagination
        if (fromIndex >= sorted.size()) return Collections.emptyList();
        toIndex = Math.min(toIndex, sorted.size());
        return sorted.subList(fromIndex, toIndex);
    }



    // Print student info
    public void printStudents(List<Student> students) {
        // Sort by rank before printing
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingInt(Student::getRank));

        for (Student s : sorted) {
            System.out.println("ID: " + s.getId() + ", Name: " + s.getName() +
                    ", Class: " + getClassName(s.getClassId()) +
                    ", Marks: " + s.getMarks() +
                    ", Status: " + s.getStatus() +
                    ", Rank: " + s.getRank() +
                    ", Gender: " + s.getGender() +
                    ", Age: " + s.getAge());

            List<Address> addrs = getAddresses(s.getId());
            for (Address a : addrs) {
                System.out.println("   Address: " + a.getCity() + " (" + a.getPincode() + ")");
            }
        }
    }


    public static void main(String[] args) {
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();

        // Add classes
        studentManagementSystem.addClassRoom(new ClassRoom(1, "A"));
        studentManagementSystem.addClassRoom(new ClassRoom(2, "B"));
        studentManagementSystem.addClassRoom(new ClassRoom(3, "C"));
        studentManagementSystem.addClassRoom(new ClassRoom(4, "D"));

        studentManagementSystem.addStudent(new Student(1, "Vihsvash", 1, 88, Gender.MALE, 10));
        studentManagementSystem.addStudent(new Student(2, "Shrishti", 1, 70, Gender.FEMALE, 11));
        studentManagementSystem.addStudent(new Student(3, "Yuvraj", 2, 88, Gender.MALE, 22)); // age is more than 20, limit exceed
        studentManagementSystem.addStudent(new Student(4, "Harsh", 2, 55, Gender.MALE, 19));
        studentManagementSystem.addStudent(new Student(5, "Sawan", 1, 88, Gender.MALE, 18));
        studentManagementSystem.addStudent(new Student(6, "Mahak", 3, 68, Gender.FEMALE, 19));
        studentManagementSystem.addStudent(new Student(7, "Amit", 3, 10, Gender.MALE, 20));
        studentManagementSystem.addStudent(new Student(8, "Sapna", 3, 0, Gender.FEMALE, 11));

        // Add addresses
        studentManagementSystem.addAddress(new Address(1, "452015", "indore", 1));
        studentManagementSystem.addAddress(new Address(2, "212056", "delhi", 1));
        studentManagementSystem.addAddress(new Address(3, "452015", "indore", 2));
        studentManagementSystem.addAddress(new Address(4, "212056", "delhi", 3));
        studentManagementSystem.addAddress(new Address(5, "452015", "indore", 4));
        studentManagementSystem.addAddress(new Address(6, "452015", "indore", 5));
        studentManagementSystem.addAddress(new Address(7, "212056", "delhi", 5));
        studentManagementSystem.addAddress(new Address(8, "482002", "mumbai", 6));
        studentManagementSystem.addAddress(new Address(9, "356748", "bhopal", 7));
        studentManagementSystem.addAddress(new Address(10, "452015", "indore", 8));


        studentManagementSystem.assignRanks();

        System.out.println(" All Students:");
        studentManagementSystem.printStudents(studentManagementSystem.studentList);

        System.out.println("\n Find students by their pincode=482002:");
        List<Student> byPin = studentManagementSystem.findStudentsByPincode("482002", null, null, null);
        studentManagementSystem.printStudents(byPin);

        // 🔹 Added usage of findStudentsByCity
        System.out.println("\n Find students by City=indore:");
        List<Student> byCity = studentManagementSystem.findStudentsByCity("indore", null, null, null);
        studentManagementSystem.printStudents(byCity);

        // 🔹 Added usage of findStudentsByClass
        System.out.println("\n Find students by Class=A:");
        List<Student> byClass = studentManagementSystem.findStudentsByClass("A", null, null, null, null);
        studentManagementSystem.printStudents(byClass);

        System.out.println("\n Passed Students:");
        studentManagementSystem.printStudents(studentManagementSystem.getPassedStudents(null, null, null, null, null));

        System.out.println("\n Failed Students:");
        studentManagementSystem.printStudents(studentManagementSystem.getFailedStudents(null, null, null, null, null));


        System.out.println("\n Female students, records 1–2 (ordered by ID default):");
        List<Student> female1to2 = studentManagementSystem.filterStudents(Gender.FEMALE, 0, 2, "id");
        studentManagementSystem.printStudents(female1to2);

        System.out.println("\n Male students, records 2-6, ordered by name:");
        List<Student> male2to6 = studentManagementSystem.filterStudents(Gender.MALE, 2, 6, "name");
        studentManagementSystem.printStudents(male2to6);

        System.out.println("\n Female students, records 1-5, ordered by marks:");
        List<Student> female1to5Marks = studentManagementSystem.filterStudents(Gender.FEMALE, 1, 5, "marks");
        studentManagementSystem.printStudents(female1to5Marks);

        System.out.println("\n Male students, records 1-6, ordered by marks:");
        List<Student> male1to6Marks = studentManagementSystem.filterStudents(Gender.MALE, 1, 6, "marks");
        studentManagementSystem.printStudents(male1to6Marks);


        System.out.println("\n Delete student id=1:");
        studentManagementSystem.deleteStudent(1);
        studentManagementSystem.printStudents(studentManagementSystem.studentList);
    }

}