import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VendingMachineUI {
    private VendingMachine machine;
    private JFrame frame;
    private JPanel gridPanel;
    private JTextArea detailsArea;
    private JLabel statusLabel;

    public VendingMachineUI(VendingMachine machine) {
        this.machine = machine;
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));

        // Grid Panel για κουμπιά προϊόντων
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(machine.getNumShelves(), 9, 5, 5));
        refreshGrid();

        // Panel για λεπτομέρειες προϊόντος
        JPanel detailPanel = new JPanel(new BorderLayout(5,5));
        detailsArea = new JTextArea(15,25);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(detailsArea);

        detailPanel.add(scrollPane, BorderLayout.CENTER);

        // Status bar
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
                    btn.setText("<html>" + p.getName() + "<br>€" + p.getFinalPrice() + "<br>x" + pos.getNumberOfProducts() + "</html>");

                    // Χρώματα ανά τύπο προϊόντος
                    if (p instanceof SolidProduct) btn.setBackground(new Color(255, 230, 200));
                    else if (p instanceof LiquidProduct) btn.setBackground(new Color(200, 230, 255));
                    btn.setOpaque(true);

                    // Hover effect
                    btn.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent e) {
                            showDetails(pos);
                        }
                        public void mouseExited(MouseEvent e) {
                            detailsArea.setText("");
                        }
                    });

                    // Κλικ για αγορά
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
        sb.append("Τιμή: €").append(p.getFinalPrice()).append("\n");
        if (p instanceof SolidProduct) sb.append("Βάρος: ").append(((SolidProduct) p).getWeight()).append("g\n");
        else if (p instanceof LiquidProduct) sb.append("Ποσότητα: ").append(((LiquidProduct) p).getQuantity()).append("ml\n");
        sb.append("Ημερομηνία λήξης: ").append(p.getExpiryDate()).append("\n");
        sb.append("Διαθέσιμα: ").append(pos.getNumberOfProducts()).append("\n");
        detailsArea.setText(sb.toString());
    }

    private void buyProduct(Position pos) {
        Product p = pos.getProduct();
        String input = JOptionPane.showInputDialog(frame, "Το προϊόν κοστίζει €" + p.getFinalPrice() + ". Εισάγετε ποσό:");

        if (input != null) {
            try {
                double amount = Double.parseDouble(input);
                if (machine.pay(p.getFinalPrice())) {
                    pos.removeProduct();
                    statusLabel.setText("Αγορά επιτυχής: " + p.getName());
                    refreshGrid();
                } else {
                    statusLabel.setText("Πληρωμή ακυρώθηκε ή ανεπαρκές ποσό.");
                }
            } catch (NumberFormatException e) {
                statusLabel.setText("Εισάγετε έγκυρο αριθμό!");
            }
        } else {
            statusLabel.setText("Ακύρωση αγοράς.");
        }
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        SwingUtilities.invokeLater(() -> new VendingMachineUI(machine));
    }
}
