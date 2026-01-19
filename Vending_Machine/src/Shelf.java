import java.util.ArrayList;

public class Shelf {
    private double height;
    private double width;
    private double depth;
    private ArrayList<Position> positions;

    public Shelf(double height, double width, double depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
        positions = new ArrayList<>();
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public void removePosition(Position position) {
        positions.remove(position);
    }

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
