package assignment3;

public enum Gender {
    MALE, FEMALE;

    public static Gender fromString(String str) {
        try {
            return Gender.valueOf(str.toUpperCase());
        } catch (Exception e) {
            return MALE; // default
        }
    }
}
