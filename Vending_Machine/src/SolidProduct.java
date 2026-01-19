public class SolidProduct extends Product {
    private double weight;  // grams

    public SolidProduct(String name, String brand, double height, double width, double length,
                        String expiryDate, int priceCents, double weight) {
        super(name, brand, height, width, length, expiryDate, priceCents);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
