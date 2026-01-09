import java.util.ArrayList;

public class Position {
    private int depth;  // Το βάθος της θέσης (πόσα όμοια προϊόντα χωράει)
    private double height;  // Το ύψος της θέσης
    private double width;   // Το πλάτος της θέσης
    private double length;  // Το μήκος της θέσης
    private int code;   // Ο κωδικός θέσης
    private ArrayList<Product> products;    // Λίστα με τα προϊόντα που περιέχει η θέση

    // Κατασκευαστής θέσης
    public Position(int depth, double height, double width, double length, int code) {
        this.depth = depth;
        this.height = height;
        this.width = width;
        this.length = length;
        this.code = code;
        products = new ArrayList<>();
    }

    // Προσθήκη προϊόντος στη λίστα των προϊόντων της θέσης
    public void addProduct(Product product)  throws ProductException {
        // Έλεγχος αν το προϊόν χωράει στη συγκεκριμένη θέση
        if (product.getHeight() <= height && product.getWidth() <= width && product.getLength() <= length) {
            // Έλεγχος αν πρόκειται για όμοιο προϊόν και αν υπάρχει διαθέσιμο βάθος στη θέση
            if ((products.isEmpty() || products.get(0).compareProducts(product)) && depth >= products.size()) {
                products.add(product);  // προσθήκη προϊόντος στη λίστα
            }
            // Εγείρεται εξαίρεση αν γίνει προσπάθεια τοποθέτησης διαφορετικού προϊόντος στη θέση
            else {
                throw new ProductException("Η θέση " + code + " περιέχει διαφορετικό προϊόν!");
            }
        }
        // Αν δε χωράει, εγείρεται εξαίρεση
        else {
            throw new ProductException("Το προϊόν \""+ product.getName() + "\" δεν χωράει στη θέση " + code + "!");
        }
    }

    // Getters μέθοδοι
    public int getCode() {
        return code;
    }

    public Product getProduct() {
        return products.get(0);
    }

    public int getNumberOfProducts() {
        return products.size(); // επιστρέφει το πλήθος των προϊόντων που έχει η θέση
    }

    public int getDepth() {
        return depth;
    }

    public void removeProduct() {
        products.remove(0); // αφαίρεση προϊόντος από τη θέση
    }

    // Έλεγχος αν η θέση περιέχει κάποιο προϊόν
    public boolean hasProduct() {
        if (!products.isEmpty()) {
            return true;
        }
        else {
            return false;
        }
    }
}
