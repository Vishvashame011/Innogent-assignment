package assignment3;

public class Address {
    private int id;
    private String pincode;
    private String city;
    private int studentId;

    public Address(int id, String pincode, String city, int studentId) {
        this.id = id;
        this.pincode = pincode;
        this.city = city;
        this.studentId = studentId;
    }

    public int getId() {
        return id;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public int getStudentId() {
        return studentId;
    }
}
