import java.util.ArrayList;

public class Shelf {
    private double height;  // Το ύψος του ραφιού
    private double width;   // Το πλάτος του ραφιού
    private double depth;   // Το βάθος του ραφιού
    private ArrayList<Position> positions;  // Λίστα με τις θέσεις του ραφιού

    // Κατασκευαστής ραφιού
    public Shelf(double height, double width, double depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
        positions = new ArrayList<>();
    }

    // Προσθήκη νέας θέσης στο ράφι
    public void addPosition(Position position) {
        positions.add(position);
    }

    // Αφαίρεση θέσεις από το ράφι
    public void removePosition(Position position) {
        positions.remove(position);
    }

    // Getters μέθοδοι
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDepth() {
        return depth;
    }

    public ArrayList<Position> getPositions() {
        return positions;
    }

    // Setters μέθοδοι
    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }
}
