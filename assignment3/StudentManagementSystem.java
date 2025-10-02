package assignment3;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentManagementSystem {
    private List<ClassRoom> classList = new ArrayList<>();
    private List<Student> studentList = new ArrayList<>();
    private List<Address> addressList = new ArrayList<>();

    // File paths
    private static final String STUDENTS_FILE = "students.csv";
    private static final String CLASSES_FILE = "classes.csv";
    private static final String ADDRESSES_FILE = "addresses.csv";
    private static final String RANKINGS_FILE = "top_rankings.csv";

    // Add a class
    public void addClassRoom(ClassRoom c) {
        classList.add(c);
    }

    // Add a student with age validation using custom exception
    public void addStudent(Student s) {
        try {
            if (s.getAge() > 20) {
                throw new InvalidAgeException("Cannot add student " + s.getName() + " - Age exceeds limit! Age: " + s.getAge());
            }
            if (s.getMarks() < 0 || s.getMarks() > 100) {
                throw new InvalidMarksException("Cannot add student " + s.getName() + " - Invalid marks! Marks: " + s.getMarks());
            }
            studentList.add(s);
        } catch (InvalidAgeException | InvalidMarksException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Add address
    public void addAddress(Address a) {
        addressList.add(a);
    }

    // File Handling Methods

    // Save all data to files
    public void saveAllData() {
        try {
            saveStudentsToFile();
            saveClassesToFile();
            saveAddressesToFile();
            System.out.println("All data saved successfully to files.");
        } catch (IOException e) {
            System.out.println("Error saving data to files: " + e.getMessage());
        }
    }

    // Load all data from files
    public void loadAllData() {
        try {
            loadClassesFromFile();
            loadStudentsFromFile();
            loadAddressesFromFile();
            System.out.println("All data loaded successfully from files.");
        } catch (IOException e) {
            System.out.println("Error loading data from files: " + e.getMessage());
        }
    }

    // Save students to file
    private void saveStudentsToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            writer.println("id,name,classId,marks,gender,age,rank");
            for (Student student : studentList) {
                writer.println(student.toString());
            }
        }
    }

    // Save classes to file
    private void saveClassesToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CLASSES_FILE))) {
            writer.println("id,name");
            for (ClassRoom classRoom : classList) {
                writer.println(classRoom.toString());
            }
        }
    }

    // Save addresses to file
    private void saveAddressesToFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ADDRESSES_FILE))) {
            writer.println("id,pincode,city,studentId");
            for (Address address : addressList) {
                writer.println(address.toString());
            }
        }
    }

    // Load students from file
    private void loadStudentsFromFile() throws IOException {
        studentList.clear();
        File file = new File(STUDENTS_FILE);
        if (!file.exists()) {
            System.out.println("Students file not found. Starting with empty student list.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                Student student = Student.fromString(line);
                if (student != null) {
                    studentList.add(student);
                }
            }
        }
    }

    // Load classes from file
    private void loadClassesFromFile() throws IOException {
        classList.clear();
        File file = new File(CLASSES_FILE);
        if (!file.exists()) {
            System.out.println("Classes file not found. Starting with empty class list.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(CLASSES_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                ClassRoom classRoom = ClassRoom.fromString(line);
                if (classRoom != null) {
                    classList.add(classRoom);
                }
            }
        }
    }

    // Load addresses from file
    private void loadAddressesFromFile() throws IOException {
        addressList.clear();
        File file = new File(ADDRESSES_FILE);
        if (!file.exists()) {
            System.out.println("Addresses file not found. Starting with empty address list.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ADDRESSES_FILE))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                Address address = Address.fromString(line);
                if (address != null) {
                    addressList.add(address);
                }
            }
        }
    }

    // Save top 5 ranked students to file
    public void saveTopRankingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RANKINGS_FILE))) {
            writer.println("Rank,Student ID,Name,Class,Marks,Gender,Age");

            List<Student> topStudents = studentList.stream()
                    .sorted(Comparator.comparingInt(Student::getRank))
                    .limit(5)
                    .collect(Collectors.toList());

            for (Student student : topStudents) {
                writer.println(student.getRank() + "," +
                        student.getId() + "," +
                        student.getName() + "," +
                        getClassName(student.getClassId()) + "," +
                        student.getMarks() + "," +
                        student.getGender() + "," +
                        student.getAge());
            }
            System.out.println("Top 5 rankings saved to " + RANKINGS_FILE);
        } catch (IOException e) {
            System.out.println("Error saving top rankings to file: " + e.getMessage());
        }
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

    // Delete student with exception handling
    public void deleteStudent(int studentId) {
        try {
            Optional<Student> studentOpt = studentList.stream().filter(s -> s.getId() == studentId).findFirst();
            if (!studentOpt.isPresent()) {
                throw new StudentNotFoundException("Student id=" + studentId + " not found.");
            }

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
        } catch (StudentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Delete class with exception handling
    public void deleteClass(int classId) {
        try {
            Optional<ClassRoom> classOpt = classList.stream().filter(c -> c.getId() == classId).findFirst();
            if (!classOpt.isPresent()) {
                throw new ClassNotFoundException("Class id=" + classId + " not found.");
            }

            // Check if class has students
            boolean hasStudents = studentList.stream().anyMatch(s -> s.getClassId() == classId);
            if (hasStudents) {
                System.out.println("Cannot delete class id=" + classId + " because it has students assigned.");
                return;
            }

            classList.removeIf(c -> c.getId() == classId);
            System.out.println("Deleted class id=" + classId);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
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

    // Method to demonstrate file operations
    public void demonstrateFileOperations() {
        System.out.println("\n=== File Operations Demo ===");

        // Save all data
        saveAllData();

        // Save top rankings
        saveTopRankingsToFile();

        // Display top 5 students
        System.out.println("\nTop 5 Ranked Students:");
        List<Student> topStudents = studentList.stream()
                .sorted(Comparator.comparingInt(Student::getRank))
                .limit(5)
                .collect(Collectors.toList());
        printStudents(topStudents);
    }

    // Method to demonstrate exception handling
    public void demonstrateExceptionHandling() {
        // Test InvalidAgeException
        System.out.println("\nTesting InvalidAgeException:");
        try {
            Student invalidAgeStudent = new Student(99, "Test Student", 1, 85, Gender.MALE, 25);
        } catch (InvalidAgeException | InvalidMarksException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // Test InvalidMarksException
        System.out.println("\nTesting InvalidMarksException:");
        try {
            Student invalidMarksStudent = new Student(100, "Test Student 2", 1, 150, Gender.FEMALE, 18);
        } catch (InvalidAgeException | InvalidMarksException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        // Test deleting non-existent student
        System.out.println("\nTesting deletion of non-existent student:");
        deleteStudent(999);

        // Test deleting non-existent class
        System.out.println("\nTesting deletion of non-existent class:");
        deleteClass(999);
    }

    public static void main(String[] args) {
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();

        // Demonstrate exception handling first
        studentManagementSystem.demonstrateExceptionHandling();

        // Try to load existing data from files
        System.out.println("\n=== Loading Existing Data from Files ===");
        studentManagementSystem.loadAllData();

        // If no data loaded, initialize with sample data
        if (studentManagementSystem.studentList.isEmpty()) {

            // Add classes
            studentManagementSystem.addClassRoom(new ClassRoom(1, "A"));
            studentManagementSystem.addClassRoom(new ClassRoom(2, "B"));
            studentManagementSystem.addClassRoom(new ClassRoom(3, "C"));
            studentManagementSystem.addClassRoom(new ClassRoom(4, "D"));

            // Add students with exception handling
            try {
                studentManagementSystem.addStudent(new Student(1, "Vihsvash", 1, 90, Gender.MALE, 10));
                studentManagementSystem.addStudent(new Student(2, "Shrishti", 1, 70, Gender.FEMALE, 11));

                // This should trigger InvalidAgeException
                try {
                    studentManagementSystem.addStudent(new Student(3, "Yuvraj", 2, 88, Gender.MALE, 22));
                } catch (InvalidAgeException e) {
                    System.out.println("Could not add student: " + e.getMessage());
                }

                studentManagementSystem.addStudent(new Student(4, "Harsh", 2, 55, Gender.MALE, 19));
                studentManagementSystem.addStudent(new Student(5, "Sawan", 1, 88, Gender.MALE, 18));
                studentManagementSystem.addStudent(new Student(6, "Mahak", 3, 68, Gender.FEMALE, 19));
                studentManagementSystem.addStudent(new Student(7, "Amit", 3, 10, Gender.MALE, 20));
                studentManagementSystem.addStudent(new Student(8, "Sapna", 3, 0, Gender.FEMALE, 11));

                // Test InvalidMarksException
                try {
                    studentManagementSystem.addStudent(new Student(9, "Invalid Marks", 1, -5, Gender.MALE, 18));
                } catch (InvalidMarksException e) {
                    System.out.println("Could not add student: " + e.getMessage());
                }

            } catch (InvalidAgeException | InvalidMarksException e) {
                System.out.println("Error creating student: " + e.getMessage());
            }

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
        }

        studentManagementSystem.assignRanks();

        System.out.println("\n=== All Students ===");
        studentManagementSystem.printStudents(studentManagementSystem.studentList);

        // Demonstrate file operations
        studentManagementSystem.demonstrateFileOperations();

        // Original functionality demonstrations
        System.out.println("\n=== Original Functionality ===");

        System.out.println("\nFind students by their pincode=482002:");
        List<Student> byPin = studentManagementSystem.findStudentsByPincode("482002", null, null, null);
        studentManagementSystem.printStudents(byPin);

        System.out.println("\nFind students by City=indore:");
        List<Student> byCity = studentManagementSystem.findStudentsByCity("indore", null, null, null);
        studentManagementSystem.printStudents(byCity);

        System.out.println("\nFind students by Class=A:");
        List<Student> byClass = studentManagementSystem.findStudentsByClass("A", null, null, null, null);
        studentManagementSystem.printStudents(byClass);

        System.out.println("\nPassed Students:");
        studentManagementSystem.printStudents(studentManagementSystem.getPassedStudents(null, null, null, null, null));

        System.out.println("\nFailed Students:");
        studentManagementSystem.printStudents(studentManagementSystem.getFailedStudents(null, null, null, null, null));

        System.out.println("\nFemale students, records 1–2 (ordered by ID default):");
        List<Student> female1to2 = studentManagementSystem.filterStudents(Gender.FEMALE, 0, 2, "id");
        studentManagementSystem.printStudents(female1to2);

        System.out.println("\nMale students, records 2-6, ordered by name:");
        List<Student> male2to6 = studentManagementSystem.filterStudents(Gender.MALE, 2, 6, "name");
        studentManagementSystem.printStudents(male2to6);

        System.out.println("\nDelete student id=1:");
        studentManagementSystem.deleteStudent(1);
        studentManagementSystem.printStudents(studentManagementSystem.studentList);

        // Save all data before exiting
        studentManagementSystem.saveAllData();
    }
}