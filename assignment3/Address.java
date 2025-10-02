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

    @Override
    public String toString() {
        return id + "," + pincode + "," + city + "," + studentId;
    }

    public static Address fromString(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 4) return null;
            int id = Integer.parseInt(parts[0]);
            String pincode = parts[1];
            String city = parts[2];
            int studentId = Integer.parseInt(parts[3]);
            return new Address(id, pincode, city, studentId);
        } catch (Exception e) {
            return null;
        }
    }
}
