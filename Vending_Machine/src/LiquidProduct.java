import java.text.DecimalFormat;

public class LiquidProduct extends Product {
    private double quantity;    // Η ποσότητα σε ml του προϊόντος σε υγρή μορφή

    // Κατασκευαστής προϊόντος σε υγρή μορφή
    public LiquidProduct(String name, String brand, double height, double width, double length, String expiryDate, double price, double quantity) {
        super(name, brand, height, width, length, expiryDate, price);   // κλήση κατασκευαστή της υπερκλάσης
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Overriding της αντίστοιχης μεθόδου για τον υπολογισμό της τελικής τιμής
    // του προϊόντος συμπεριλαμβανομένου του ΦΠΑ
    public double getFinalPrice() {
        // Αν το προϊόν πρόκειται για εμφιαλωμένο νερό, υπολόγισε την τελική τιμή με ΦΠΑ 13%
        if (getName().contains("Νερό")) {
            DecimalFormat df = new DecimalFormat("0.00");
            double res = super.getPrice() * (1 + 0.13);
            return Double.parseDouble(df.format(res));
        }
        // Διαφορετικά κάλεσε την αντίστοιχη συνάρτηση της υπερκλάσης
        else {
            return super.getFinalPrice();
        }
    }
}
