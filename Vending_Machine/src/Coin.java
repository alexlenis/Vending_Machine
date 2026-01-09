public class Coin {
    private double value;   // Η αξία του κέρματος

    // Κατασκευαστής κέρματος
    public Coin(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
