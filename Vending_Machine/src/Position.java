public class Position {
    private int depth;
    private double height;
    private double width;
    private double length;
    private int code;

    private Product product; // τύπος προϊόντος
    private int quantity;    // πόσα διαθέσιμα

    public Position(int depth, double height, double width, double length, int code) {
        this.depth = depth;
        this.height = height;
        this.width = width;
        this.length = length;
        this.code = code;
        this.product = null;
        this.quantity = 0;
    }

    public void addProduct(Product p) throws ProductException {
        if (p.getHeight() > height || p.getWidth() > width || p.getLength() > length) {
            throw new ProductException("Το προϊόν \"" + p.getName() + "\" δεν χωράει στη θέση " + code + "!");
        }

        if (product == null) {
            product = p;
        } else {
            if (!product.compareProducts(p)) {
                throw new ProductException("Η θέση " + code + " περιέχει διαφορετικό προϊόν!");
            }
        }

        if (quantity >= depth) {
            throw new ProductException("Η θέση " + code + " είναι γεμάτη!");
        }

        quantity++;
    }

    public int getCode() {
        return code;
    }

    public Product getProduct() {
        return product;
    }

    public int getNumberOfProducts() {
        return quantity;
    }

    public int getDepth() {
        return depth;
    }

    public void removeProduct() throws ProductException {
        if (quantity <= 0) {
            throw new ProductException("Η θέση " + code + " είναι άδεια!");
        }
        quantity--;
        if (quantity == 0) {
            product = null;
        }
    }

    public boolean hasProduct() {
        return quantity > 0 && product != null;
    }
}
