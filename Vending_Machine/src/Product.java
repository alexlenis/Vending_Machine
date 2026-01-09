import java.text.DecimalFormat;

public abstract class Product {
    private String name;    // Το όνομα του προϊόντος
    private double height;  // Το ύψος του προϊόντος
    private double width;   // Το πλάτος του προϊόντος
    private double length;  // Το μήκος του προϊόντος
    private String expiryDate;  // Η ημερομηνία λήξης του προϊόντος
    private double price;   // Η τιμή του προϊόντος χωρίς τον ΦΠΑ
    private String brand;   // Η μάρκα του προϊόντος

    // Κατασκευαστής προϊόντος
    public Product(String name, String brand, double height, double width, double length, String expiryDate, double price) {
        this.name = name;
        this.brand = brand;
        this.height = height;
        this.width = width;
        this.length = length;
        this.expiryDate = expiryDate;
        this.price = price;
    }

    // Getters μέθοδοι
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
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

    // Setters μέθοδοι
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
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

    // Υπολογισμός της τελικής τιμής του προϊόντος συμπεριλαμβανομένου του ΦΠΑ 24%
    public double getFinalPrice() {
        DecimalFormat df = new DecimalFormat("0.00");
        double res = this.price * (1 + 0.24);
        return Double.parseDouble(df.format(res));
    }

    // Έλεγχος για όμοια προϊόντα (με ίδια χαρακτηριστικά)
    public boolean compareProducts(Product product) {
        return name.equals(product.getName()) && height == product.getHeight()
                && width == product.getWidth() && length == product.getLength() && price == product.getPrice()
                && brand.equals(product.getBrand());
    }
}
