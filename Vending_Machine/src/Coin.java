public class Coin {
    private final int cents; // αξία σε λεπτά

    public Coin(int cents) {
        this.cents = cents;
    }

    public int getCents() {
        return cents;
    }

    public double getValue() {
        return cents / 100.0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", getValue());
    }
}
