package assignment3;

public class ClassRoom {
    private int id;
    private String name;

    public ClassRoom(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + "," + name;
    }

    public static ClassRoom fromString(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 2) return null;
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            return new ClassRoom(id, name);
        } catch (Exception e) {
            return null;
        }
    }
}