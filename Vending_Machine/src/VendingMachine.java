import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class VendingMachine {
    private int numShelves; // Ο αριθμός των ραφιών της μηχανής
    private Shelf[] shelves;    // Πίνακας με τα ράφια της μηχανής
    private ArrayList<Coin> coins;  // Λίστα με τα κέρματα της μηχανής
    private Random rand;    // Αντικείμενο Random για την παραγωγή τυχαίων αριθμών
    private DecimalFormat df = new DecimalFormat("0.00"); // Αντικείμενο DecimalFormat για μορφοποίηση των αριθμών

    // Ο κατασκευαστής της μηχανής
    public VendingMachine() {
        // Αρχικοποίηση των ιδιοτήτων
        this.numShelves = 5;    // Η μηχανή έχει 5 ράφια
        shelves = new Shelf[numShelves];
        coins = new ArrayList<>();
        rand = new Random();

        double totalHeight = 0; // Το συνολικό ύψος των ραφιών
        int[] NumOfPositions = {5, 5, 9, 6, 9}; // Πλήθος θέσεων ανά ράφι
        int code = 11;  // Κωδικός για τις θέσεις προϊόντων

        // Διαδικασία δημιουργίας ραφιών με θέσεις
        for (int i = 0, j = 0; i < numShelves; i++, j++) {
            // Δημιουργία τυχαίων διαστάσεων ραφιού
            double height = rand.nextDouble() * (0.2 - 0.1) + 10.;
            double width = rand.nextDouble() * (1.5 - 0.1) + 0.1;
            double depth = rand.nextDouble() * (1.5 - 0.1) + 0.1;

            // Έλεγχος αν το συνολικό ύψος των ραφιών δεν υπερβαίνει το 1 μ.
            if (totalHeight + height > 1.0) {
                throw new IllegalArgumentException("Το συνολικό ύψος των ραφιών υπερβαίνει το 1 μ.");
            }

            shelves[i] = new Shelf(height, width, depth);   // Δημιουργία του ραφιού και προσθήκη στον πίνακα shelves
            totalHeight += height;

            // Δημιουργία τυχαίων διαστάσεων για τις θέσεις του ραφιού
            int pos_depth = rand.nextInt(5) + 1;
            double pos_height = rand.nextDouble() * (height - 0.1) + 0.1;
            double pos_width = rand.nextDouble() * (width - 0.1) + 0.1;
            double pos_length = rand.nextDouble() * (0.2 - 0.05) + 0.05;

            // Δημιουργία θέσεων με τις παραπάνω διαστάσεις για κάθε ράφι
            for (int k = 0; k < NumOfPositions[j]; k++) {
                shelves[i].addPosition(new Position(pos_depth, pos_height, pos_width, pos_length, code));
                code++;
            }
        }
    }
      public int getNumShelves() {
        return numShelves;
    }

    public Shelf[] getShelves() {
        return shelves;
    }

    // Εκτύπωση της κατάστασης της μηχανής
    public void printMachine() {
        // Για κάθε ράφι, εκτύπωσε τον αριθμό της θέσης, το όνομα του προϊόντος, πόσα ίδια προϊόντα υπάρχουν στη θέση
        // και την τελική τιμή με τον ΦΠΑ για το συγκεκριμένο προϊόν
        for (int i = 0; i < numShelves; i++) {
            System.out.println("\n+++++++++++++++++++++++++++ ΡΑΦΙ " + (i + 1) + " +++++++++++++++++++++++++++");
            for (int j = 0; j < shelves[i].getPositions().size(); j++) {
                if (shelves[i].getPositions().get(j).hasProduct()) {
                    System.out.printf("%3d\t%-40s\t%2d\t%8.2f\n", shelves[i].getPositions().get(j).getCode(),
                            shelves[i].getPositions().get(j).getProduct().getName(),
                            shelves[i].getPositions().get(j).getNumberOfProducts(),
                            shelves[i].getPositions().get(j).getProduct().getFinalPrice());
                }
                // Αν η θέση δεν περιέχει κάποιο προϊόν, εκτύπωσε ότι είναι άδειο
                else {
                    System.out.printf("%3d\t%-40s\n", shelves[i].getPositions().get(j).getCode(), "άδειο");
                }
            }
            System.out.print("\n");
        }
    }

    // Υπολογισμός του πλήθους των κερμάτων της μηχανής με συγκεκριμένο value
    public int getCoinCount(double value) {
        int count = 0;
        for (Coin coin : coins) {
            if (value == coin.getValue()) {
                count++;
            }
        }
        return count;
    }

    // Αφαίρεση κέρματος με συγκεκριμένο value από τα κέρματα της μηχανής
    public void removeCoin(double value) {
        for (int i = 0; i < coins.size(); i++) {
            if (value == coins.get(i).getValue()) {
                coins.remove(coins.get(i));
                break;
            }
        }
    }

    // Συνάρτηση που μοντελοποιεί την πιθανότητα 20% να μη δεχτεί η μηχανή το κέρμα του χρήστη
    // και 80% να το δεχτεί
    public boolean isCoinAccepted() {
        int probability = rand.nextInt(100) + 1; // Δημιουργία τυχαίου αριθμού από το 1 έως το 100
        return probability <= 80; // Πιθανότητα 80% να δεχτεί το κέρμα
    }

    // Έλεγχος αν το value ανήκει στα αποδεκτά κέρματα της μηχανής
    public boolean checkCoin(double value) {
        double[] coin_values = {0.01, 0.02, 0.05, 0.1, 0.2, 1, 2};
        for (double coin_value : coin_values) {
            if (value == coin_value) {
                return true;
            }
        }
        return false;
    }

    // Συνάρτηση πληρωμής προϊόντος
    public boolean pay(double amount) {
        ArrayList<Coin> inserted_coins = new ArrayList<>(); // Λίστα για τα κέρματα που εισάγει ο χρήστης
        double money_sum = 0;   // Το συνολικό ποσό των εισαχθέντων κερμάτων
        Scanner scanner = new Scanner(System.in);

        // Επανάλαβε μέχρι να συμπληρωθεί το απαιτούμενο ποσό
        while (money_sum < amount) {
            System.out.print("Εισάγετε ποσό ή πατήστε CANCEL για επιστροφή χρημάτων: ");
            String input = scanner.nextLine();

            // Αν ο χρήστης πατήσει CANCEL, επιστρέφονται (αν υπάρχουν) τα εισαχθέντα κέρματα
            if (input.equals("CANCEL")) {
                if (!inserted_coins.isEmpty()) {
                    System.out.print("Επιστροφή των κερμάτων: ");
                    for (Coin inserted_coin : inserted_coins) {
                        System.out.print(inserted_coin.getValue() + " ");
                        removeCoin(inserted_coin.getValue());   // αφαίρεση κέρματος από τη μηχανή
                    }
                    inserted_coins.clear(); // διαγραφή περιεχομένων της λίστας inserted_coins
                    System.out.print("\n");
                }
                return false;   // επιστρέφει false ότι δεν έγινε η πληρωμή
            }

            // Αν η μηχανή δεχτεί το κέρμα και αν το value του είναι έγκυρο
            else if (isCoinAccepted() && checkCoin(Double.parseDouble(input))) {
                double val = Double.parseDouble(input);
                money_sum += val;   // προσθήκη αξίας στο συνολικό ποσό
                money_sum = Double.parseDouble(df.format(money_sum));
                inserted_coins.add(new Coin(val));  // προσθήκη κέρματος στη λίστα inserted_coins
                coins.add(new Coin(val));   // προσθήκη κέρματος στα κέρματα της μηχανής
                System.out.println("Τρέχον συνολικό ποσό: " + money_sum);   // εκτύπωση τρέχοντος συνολικού ποσού
            }
        }

        System.out.println("Συμπληρώσατε το απαιτούμενο ποσό. Παρακαλώ, παραλάβετε το προϊόν σας.");

        // Αν υπάρχουν ρέστα
        if (money_sum > amount) {
            computeChange(money_sum - amount);
        }

        return true;    // επιστρέφει true ότι έγινε η πληρωμή
    }

    // Συνάρτηση υπολογισμού και εκτύπωσης των ρέστων
    public void computeChange(double amount) {
        ArrayList<Coin> change = new ArrayList<>(); // Λίστα για τα κέρματα των ρέστων
        double[] coin_values = {2, 1, 0.2, 0.1, 0.05, 0.02, 0.01};

        // Για κάθε αξία κέρματος
        for (double coin_value : coin_values) {
            while (amount >= coin_value) {
                // Σταμάτα αν δεν υπάρχουν στη μηχανή άλλα κέρματα αυτού του coin_value
                if (getCoinCount(coin_value) == 0) {
                    break;
                }
                change.add(new Coin(coin_value));   // προσθήκη κέρματος στη λίστα των ρέστων
                amount -= coin_value;   // μείωση συνολικού ποσού των ρέστων
                amount = Double.parseDouble(df.format(amount));
                //System.out.println(amount);
                removeCoin(coin_value); // αφαίρεση κέρματος από τα κέρματα της μηχανής
            }
        }

        if (!change.isEmpty()) {
            System.out.println("Παρακαλώ, παραλάβετε τα ρέστα σας:");
        }

        // Εμφάνιση των κερμάτων της λίστας change
        for (Coin coin : change) {
            System.out.println(coin.getValue());
        }

        // Αν το ποσό των ρέστων δεν είναι 0, ενημέρωσε τον χρήστη πως δεν επαρκούν τα κέρματα της μηχανής
        if (amount != 0) {
            System.out.println("Τα κέρματα της μηχανής δεν επαρκούν για τα ρέστα.");
        }
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        // Δημιουργία προϊόντων και προσθήκη τους σε συγκεκριμένες θέσεις των ραφιών
        try {
            machine.shelves[0].getPositions().get(3).addProduct(new SolidProduct("Μπάρα δημητριακών με μέλι και φουντούκια", "NutriBar", 0.1, 0.04, 0.02, "15/12/2023", 0.8, 50));
            machine.shelves[0].getPositions().get(3).addProduct(new SolidProduct("Μπάρα δημητριακών με μέλι και φουντούκια", "NutriBar", 0.1, 0.04, 0.02, "15/12/2023", 0.8, 50));
        } catch (ProductException e) {}
        try {
            machine.shelves[0].getPositions().get(0).addProduct(new SolidProduct("Σοκολατένια μπάρα με φουντούκια", "ChocoNut", 0.1, 0.04, 0.015, "15/12/2023", 1, 40));
        } catch (ProductException e) {}
        try {
            machine.shelves[1].getPositions().get(1).addProduct(new SolidProduct("Πατατάκια με γεύση BBQ", "Lays", 0.11, 0.07, 0.03, "30/10/2024", 1, 80));
        } catch (ProductException e) {}
        try {
            machine.shelves[1].getPositions().get(3).addProduct(new SolidProduct("Doritos Nachos", "Doritos", 0.1, 0.06, 0.02, "31/10/2023", 1.2, 100));
        } catch (ProductException e) {}
        try {
            machine.shelves[2].getPositions().get(1).addProduct(new SolidProduct("Ποπ Κορν Βουτύρου", "Tasty", 0.11, 0.06, 0.04, "10/07/2025", 1.27, 86));
        } catch (ProductException e) {}
        try {
            machine.shelves[2].getPositions().get(2).addProduct(new SolidProduct("Μπισκότα βουτύρου με σοκολάτα", "Παπαδοπούλου", 0.08, 0.05, 0.02, "30/10/2024", 0.8, 90));
        } catch (ProductException e) {}
        try {
            machine.shelves[2].getPositions().get(5).addProduct(new SolidProduct("Καραμέλες μέντας", "Mentos", 0.1, 0.05, 0.02, "10/09/2024", 0.5, 25));
            machine.shelves[2].getPositions().get(5).addProduct(new SolidProduct("Καραμέλες μέντας", "Mentos", 0.1, 0.05, 0.02, "10/09/2024", 0.5, 25));
            machine.shelves[2].getPositions().get(5).addProduct(new SolidProduct("Καραμέλες μέντας", "Mentos", 0.1, 0.05, 0.02, "10/09/2024", 0.5, 25));
        } catch (ProductException e) {}
        try {
            machine.shelves[3].getPositions().get(1).addProduct(new SolidProduct("Κρουασάν σοκολάτας", "Molto", 0.1, 0.06, 0.05, "30/06/2024", 1.2, 80));
        } catch (ProductException e) {}
        try {
            machine.shelves[3].getPositions().get(3).addProduct(new SolidProduct("Κρουασάν με γέμιση φράουλα", "Molto", 0.1, 0.06, 0.05, "28/08/2024", 1.2, 80));
        } catch (ProductException e) {}
        try {
            machine.shelves[4].getPositions().get(3).addProduct(new LiquidProduct("Νερό Ζαγόρι 0.5L", "Ζαγόρι", 0.1, 0.06, 0.04, "23/09/2024", 0.5, 500));
            machine.shelves[4].getPositions().get(3).addProduct(new LiquidProduct("Νερό Ζαγόρι 0.5L", "Ζαγόρι", 0.1, 0.06, 0.04, "30/10/2024", 0.5, 500));
            machine.shelves[4].getPositions().get(3).addProduct(new LiquidProduct("Νερό Ζαγόρι 0.5L", "Ζαγόρι", 0.1, 0.06, 0.04, "12/05/2024", 0.5, 500));
        } catch (ProductException e) {}
        try {
            machine.shelves[4].getPositions().get(0).addProduct(new LiquidProduct("Αναψυκτικό Cola 330ml", "Coca Cola", 0.09, 0.06, 0.06, "30/06/2024", 1.5, 330));
        } catch (ProductException e) {}
        try {
            machine.shelves[4].getPositions().get(2).addProduct(new LiquidProduct("Ενεργειακό ποτό PowerBoost 250ml", "EnergyMax", 0.1, 0.05, 0.05, "10/04/2024", 1.5, 250));
        } catch (ProductException e) {}
        try {
            machine.shelves[4].getPositions().get(7).addProduct(new LiquidProduct("Φυσικός χυμός πορτοκάλι 200ml", "Ήβη", 0.1, 0.04, 0.04, "25/07/2024", 1.2, 200));
        } catch (ProductException e) {}

        // Προσθήκη κερμάτων στη μηχανή
        for (int i = 0; i < 2; i++) {
            machine.coins.add(new Coin(0.01));
            machine.coins.add(new Coin(0.02));
            machine.coins.add(new Coin(0.05));
            machine.coins.add(new Coin(0.1));
            machine.coins.add(new Coin(0.2));
            machine.coins.add(new Coin(1));
            machine.coins.add(new Coin(2));
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            machine.printMachine();
            System.out.print("Εισάγετε ένα διψήφιο κωδικό θέσης προϊόντος (11-99) ή πατήστε 0 για ΕΞΟΔΟ: ");
            int code = scanner.nextInt();

            if (code == 0) {
                return; // έξοδος από το πρόγραμμα
            }
            else {
                int found = 0;
                // Ψάξε σε όλα τα ράφια της μηχανής για να βρεις τη θέση με τον κωδικό που έδωσε ο χρήστης
                for (int i = 0; i < machine.numShelves; i++) {
                    for (int j = 0; j < machine.shelves[i].getPositions().size(); j++) {
                        // Αν υπάρχει αυτός ο κωδικός θέσης και υπάρχει προϊόν σε αυτή τη θέση,
                        // εκτύπωση της τιμής του προϊόντος και εκκίνηση της διαδικασίας πληρωμής
                        if (machine.shelves[i].getPositions().get(j).getCode() == code && machine.shelves[i].getPositions().get(j).hasProduct()) {
                            found = 1;
                            System.out.println("Το προϊόν κοστίζει " + machine.shelves[i].getPositions().get(j).getProduct().getFinalPrice() + "€");
                            // Αν γίνει επιτυχώς η πληρωμή, αφαίρεσε το προϊόν από τη θέση
                            if (machine.pay(machine.shelves[i].getPositions().get(j).getProduct().getFinalPrice())) {
                                machine.shelves[i].getPositions().get(j).removeProduct();
                            }
                        }
                    }
                }

                // Αν δεν υπάρχει προϊόν στη συγκεκριμένη θέση
                if (found == 0) {
                    System.out.println("Δεν υπάρχει προϊόν στη θέση " + code + "!");
                }
            }
        }

    }
}
