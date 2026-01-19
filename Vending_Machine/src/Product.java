public abstract class Product {
    private String name;
    private double height;
    private double width;
    private double length;
    private String expiryDate;
    private int priceCents;   // τιμή χωρίς ΦΠΑ σε cents
    private String brand;

    public Product(String name, String brand, double height, double width, double length, String expiryDate, int priceCents) {
        this.name = name;
        this.brand = brand;
        this.height = height;
        this.width = width;
        this.length = length;
        this.expiryDate = expiryDate;
        this.priceCents = priceCents;
    }

    public String getName() {
        return name;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public String getBrand() {
        return brand;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    // default ΦΠΑ 24%
    public int getFinalPriceCents() {
        return (int) Math.round(priceCents * 1.24);
    }

    public double getFinalPrice() {
        return getFinalPriceCents() / 100.0;
    }

    public boolean compareProducts(Product product) {
        return name.equals(product.getName())
                && Double.compare(height, product.getHeight()) == 0
                && Double.compare(width, product.getWidth()) == 0
                && Double.compare(length, product.getLength()) == 0
                && priceCents == product.getPriceCents()
                && brand.equals(product.getBrand());
    }
}
