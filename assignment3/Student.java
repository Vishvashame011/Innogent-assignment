package assignment3;

import java.util.Objects;

public class Student {
    private int id;
    private String name;
    private int classId;
    private int marks;
    private int age;
    private int rank;
    Gender gender;

    public Student(int id, String name, int classId, int marks, Gender gender, int age)
            throws InvalidAgeException, InvalidMarksException {

        // Validate age
        if (age > 20) {
            throw new InvalidAgeException("Student age cannot be greater than 20. Provided age: " + age);
        }

        // Validate marks
        if (marks < 0 || marks > 100) {
            throw new InvalidMarksException("Marks must be between 0 and 100. Provided marks: " + marks);
        }

        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassId() {
        return classId;
    }

    public int getMarks() {
        return marks;
    }

    public int getAge() {
        return age;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Gender getGender() {
        return gender;
    }

    public String getStatus() {
        return marks < 50 ? "Failed" : "Passed";
    }

    @Override
    public String toString() {
        return id + "," + name + "," + classId + "," + marks + "," + gender + "," + age + "," + rank;
    }

    public static Student fromString(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 7) return null;
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            int classId = Integer.parseInt(parts[2]);
            int marks = Integer.parseInt(parts[3]);
            Gender gender = Gender.fromString(parts[4]);
            int age = Integer.parseInt(parts[5]);
            int rank = Integer.parseInt(parts[6]);

            Student student = new Student(id, name, classId, marks, gender, age);
            student.setRank(rank);
            return student;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}