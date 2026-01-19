import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

public class VendingMachineUI {
    private VendingMachine machine;
    private JFrame frame;
    private JPanel gridPanel;
    private JTextArea detailsArea;
    private JLabel statusLabel;

    public VendingMachineUI(VendingMachine machine) {
        this.machine = machine;
        seedProducts(); // γεμίζουμε προϊόντα
        createUI();
    }

    private void seedProducts() {
        try {
            machine.getShelves()[0].getPositions().get(0)
                    .addProduct(new SolidProduct("Σοκολάτα", "ChocoNut", 0.1, 0.04, 0.02, "15/12/2026", 100, 40));
            machine.getShelves()[0].getPositions().get(0)
                    .addProduct(new SolidProduct("Σοκολάτα", "ChocoNut", 0.1, 0.04, 0.02, "15/12/2026", 100, 40));

            machine.getShelves()[4].getPositions().get(0)
                    .addProduct(new LiquidProduct("Νερό 0.5L", "Ζαγόρι", 0.1, 0.06, 0.04, "23/09/2026", 50, 500));
            machine.getShelves()[4].getPositions().get(0)
                    .addProduct(new LiquidProduct("Νερό 0.5L", "Ζαγόρι", 0.1, 0.06, 0.04, "23/09/2026", 50, 500));
        } catch (ProductException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createUI() {
        frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(machine.getNumShelves(), 9, 5, 5));
        refreshGrid();

        JPanel detailPanel = new JPanel(new BorderLayout(5, 5));
        detailsArea = new JTextArea(15, 25);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        detailPanel.add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Καλώς ήρθατε!");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(detailPanel, BorderLayout.EAST);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refreshGrid() {
        gridPanel.removeAll();

        for (int i = 0; i < machine.getNumShelves(); i++) {
            for (Position pos : machine.getShelves()[i].getPositions()) {
                JButton btn = new JButton();

                if (pos.hasProduct()) {
                    Product p = pos.getProduct();
                    btn.setText("<html>" + p.getName()
                            + "<br>" + VendingMachine.formatCents(p.getFinalPriceCents())
                            + "<br>x" + pos.getNumberOfProducts() + "</html>");

                    if (p instanceof SolidProduct) btn.setBackground(new Color(255, 230, 200));
                    else if (p instanceof LiquidProduct) btn.setBackground(new Color(200, 230, 255));
                    btn.setOpaque(true);

                    btn.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) {
                            showDetails(pos);
                        }

                        public void mouseExited(MouseEvent e) {
                            detailsArea.setText("");
                        }
                    });

                    btn.addActionListener(e -> buyProduct(pos));
                } else {
                    btn.setText("Άδειο");
                    btn.setEnabled(false);
                }

                gridPanel.add(btn);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showDetails(Position pos) {
        Product p = pos.getProduct();
        StringBuilder sb = new StringBuilder();
        sb.append("Όνομα: ").append(p.getName()).append("\n");
        sb.append("Μάρκα: ").append(p.getBrand()).append("\n");
        sb.append("Διαστάσεις: ").append(p.getHeight()).append("x").append(p.getWidth()).append("x").append(p.getLength()).append("\n");
        sb.append("Τιμή: ").append(VendingMachine.formatCents(p.getFinalPriceCents())).append("\n");

        if (p instanceof SolidProduct) sb.append("Βάρος: ").append(((SolidProduct) p).getWeight()).append("g\n");
        else if (p instanceof LiquidProduct) sb.append("Ποσότητα: ").append(((LiquidProduct) p).getQuantity()).append("ml\n");

        sb.append("Ημερομηνία λήξης: ").append(p.getExpiryDate()).append("\n");
        sb.append("Διαθέσιμα: ").append(pos.getNumberOfProducts()).append("\n");
        detailsArea.setText(sb.toString());
    }

    private void buyProduct(Position pos) {
        Product p = pos.getProduct();
        int priceCents = p.getFinalPriceCents();

        String input = JOptionPane.showInputDialog(frame,
                "Το προϊόν κοστίζει " + VendingMachine.formatCents(priceCents) + "\nΕισάγετε ποσό (π.χ. 2.00):");

        if (input == null) {
            statusLabel.setText("Ακύρωση αγοράς.");
            return;
        }

        try {
            input = input.trim().replace(",", ".");
            double amount = Double.parseDouble(input);
            int insertedCents = (int) Math.round(amount * 100);

            Optional<List<Integer>> result = machine.pay(priceCents, insertedCents);

            if (!result.isPresent()) {
                statusLabel.setText("Αποτυχία πληρωμής (ανεπαρκές ποσό ή δεν υπάρχουν ρέστα).");
                return;
            }

            // αγορά επιτυχής
            pos.removeProduct();

            List<Integer> change = result.get();
            if (change.isEmpty()) {
                statusLabel.setText("Αγορά επιτυχής: " + p.getName() + " (χωρίς ρέστα)");
            } else {
                StringBuilder sb = new StringBuilder("Ρέστα: ");
                for (int c : change) sb.append(VendingMachine.formatCents(c)).append(" ");
                statusLabel.setText("Αγορά επιτυχής: " + p.getName() + " | " + sb);
            }

            refreshGrid();

        } catch (NumberFormatException ex) {
            statusLabel.setText("Εισάγετε έγκυρο αριθμό (π.χ. 1.50)!");
        } catch (ProductException ex) {
            statusLabel.setText(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        SwingUtilities.invokeLater(() -> new VendingMachineUI(machine));
    }
}
