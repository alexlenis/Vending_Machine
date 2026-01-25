public class Main {
    public static void main(String[] args) {
        System.out.println("=== Vending Machine (Docker Headless Demo) ===");

        try {
            VendingMachine machine = new VendingMachine();
            System.out.println("Machine created successfully: " + machine);
            System.out.println("DONE ✅");
        } catch (Exception e) {
            System.out.println("ERROR ❌ " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
