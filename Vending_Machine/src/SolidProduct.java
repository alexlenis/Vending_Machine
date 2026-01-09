public class SolidProduct extends Product {
    private double weight;  // Το βάρος σε gr του προϊόντος σε στερεά μορφή

    // Κατασκευαστής προϊόντος σε στερεά μορφή
    public SolidProduct(String name, String brand, double height, double width, double length, String expiryDate, double price, double weight) {
        super(name, brand, height, width, length, expiryDate, price);   // κλήση κατασκευαστή της υπερκλάσης
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
