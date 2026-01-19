public class LiquidProduct extends Product {
    private double quantity; // ml

    public LiquidProduct(String name, String brand, double height, double width, double length,
                         String expiryDate, int priceCents, double quantity) {
        super(name, brand, height, width, length, expiryDate, priceCents);
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    // Νερό με ΦΠΑ 13%
    @Override
    public int getFinalPriceCents() {
        if (getName().contains("Νερό")) {
            return (int) Math.round(getPriceCents() * 1.13);
        }
        return super.getFinalPriceCents();
    }
}
