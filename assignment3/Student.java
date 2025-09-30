package assignment3;

public class Student {
    private int id;
    private String name;
    private int classId;
    private int marks;
    private int age;

    private int rank;
    Gender gender;


    public Student(int id, String name, int classId, int marks, Gender gender, int age) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.marks = marks;
        this.age = age;
        this.gender = gender;}

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
}
