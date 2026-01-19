import java.util.*;

public class VendingMachine {
    private int numShelves;
    private Shelf[] shelves;

    // coin inventory: πόσα κέρματα έχει η μηχανή
    private final Map<Integer, Integer> coinInventory = new HashMap<>();

    public VendingMachine() {
        this.numShelves = 5;
        shelves = new Shelf[numShelves];

        int[] NumOfPositions = {5, 5, 9, 6, 9};
        int code = 11;

        // σταθερά “λογικές” διαστάσεις
        for (int i = 0; i < numShelves; i++) {
            shelves[i] = new Shelf(0.15, 0.8, 0.8);

            for (int k = 0; k < NumOfPositions[i]; k++) {
                shelves[i].addPosition(new Position(5, 0.15, 0.25, 0.25, code));
                code++;
            }
        }

        // αρχικά κέρματα (20 από το καθένα για να έχει ρέστα)
        int[] coins = {1, 2, 5, 10, 20, 100, 200};
        for (int c : coins) {
            coinInventory.put(c, 20);
        }
    }

    public int getNumShelves() {
        return numShelves;
    }

    public Shelf[] getShelves() {
        return shelves;
    }

    public void addCoinToMachine(int cents) {
        coinInventory.put(cents, coinInventory.getOrDefault(cents, 0) + 1);
    }

    public boolean hasCoin(int cents) {
        return coinInventory.getOrDefault(cents, 0) > 0;
    }

    public void removeCoinFromMachine(int cents) {
        int count = coinInventory.getOrDefault(cents, 0);
        if (count > 0) {
            coinInventory.put(cents, count - 1);
        }
    }

    public boolean isValidCoin(int cents) {
        return cents == 1 || cents == 2 || cents == 5 || cents == 10 || cents == 20 || cents == 100 || cents == 200;
    }

    // υπολογισμός ρέστων με greedy (χωρίς να χαλάει το inventory αν αποτύχει)
    public List<Integer> computeChange(int changeCents) {
        List<Integer> change = new ArrayList<>();
        int[] coinValues = {200, 100, 20, 10, 5, 2, 1};

        // κάνουμε προσωρινό αντίγραφο
        Map<Integer, Integer> tempInventory = new HashMap<>(coinInventory);

        for (int coin : coinValues) {
            while (changeCents >= coin && tempInventory.getOrDefault(coin, 0) > 0) {
                change.add(coin);
                changeCents -= coin;
                tempInventory.put(coin, tempInventory.get(coin) - 1);
            }
        }

        // αν δεν μπορέσαμε να δώσουμε ακριβή ρέστα
        if (changeCents != 0) {
            return new ArrayList<>();
        }

        // εφαρμόζουμε τις αφαιρέσεις στο πραγματικό inventory
        for (int coin : change) {
            removeCoinFromMachine(coin);
        }

        return change;
    }

    /**
     * Πληρωμή προϊόντος.
     * @param priceCents η τιμή σε cents
     * @param insertedCents πόσα cents έδωσε ο χρήστης
     * @return Optional με λίστα ρέστων (σε cents). Αν empty => αποτυχία (ανεπαρκές ποσό ή δεν υπάρχουν ρέστα)
     */
    public Optional<List<Integer>> pay(int priceCents, int insertedCents) {
        if (insertedCents < priceCents) {
            return Optional.empty();
        }

        int changeCents = insertedCents - priceCents;

        if (changeCents == 0) {
            return Optional.of(new ArrayList<>());
        }

        List<Integer> change = computeChange(changeCents);

        // Αν δεν έχουμε ρέστα, αποτυχία
        if (change.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(change);
    }

    // helper για εμφάνιση
    public static String formatCents(int cents) {
        return String.format("€%.2f", cents / 100.0);
    }
}
