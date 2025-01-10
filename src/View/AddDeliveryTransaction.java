package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import Controller.*;
import Model.CurrentCustomer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddDeliveryTransaction {
    private JComboBox<String> categoryDeliveryComboBox;
    private DatabaseHandler conn;
    private ArrayList<Double> fees;

    public AddDeliveryTransaction() {
        try {
            conn = new DatabaseHandler();
            conn.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal" + e.getMessage(), null, 0);
        }
        showDeliveryView();
    }

    private void loadCategoryData() {
        fees = new ArrayList<>();
        try {
            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT category_name, fee_per_kg FROM category_delivery");

            while (rs.next()) {
                categoryDeliveryComboBox.addItem(rs.getString("category_name"));
                fees.add(rs.getDouble("fee_per_kg"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading categories: " + e.getMessage());
        }
    }

    public void showDeliveryView() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JFrame deliveryFrame = new JFrame("Add Delivery Transaction");
        deliveryFrame.setLayout(null);
        deliveryFrame.setSize(400, 450);
        deliveryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = (screenSize.width - deliveryFrame.getWidth()) / 2;
        int y = (screenSize.height - deliveryFrame.getHeight()) / 2;
        deliveryFrame.setLocation(x, y);

        JLabel title = new JLabel("Delivery Transaction");
        title.setBounds(80, 20, 350, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        deliveryFrame.add(title);

        //
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 25);
        deliveryFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 80, 200, 25);
        deliveryFrame.add(nameField);

        //
        JLabel alamatLabel = new JLabel("Alamat:");
        alamatLabel.setBounds(50, 120, 100, 25);
        deliveryFrame.add(alamatLabel);

        JTextField alamatField = new JTextField();
        alamatField.setBounds(150, 120, 200, 25);
        deliveryFrame.add(alamatField);

        //
        JLabel noTelpLabel = new JLabel("No. Telp:");
        noTelpLabel.setBounds(50, 160, 100, 25);
        deliveryFrame.add(noTelpLabel);

        JTextField noTelpField = new JTextField();
        noTelpField.setBounds(150, 160, 200, 25);
        deliveryFrame.add(noTelpField);

        //
        JLabel beratLabel = new JLabel("Berat (kg):");
        beratLabel.setBounds(50, 200, 100, 25);
        deliveryFrame.add(beratLabel);

        JTextField weightField = new JTextField();
        weightField.setBounds(150, 200, 200, 25);
        deliveryFrame.add(weightField);

        //
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 240, 100, 25);
        deliveryFrame.add(categoryLabel);

        categoryDeliveryComboBox = new JComboBox<>();
        categoryDeliveryComboBox.setBounds(150, 240, 200, 25);
        deliveryFrame.add(categoryDeliveryComboBox);
        loadCategoryData();

        //
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 280, 200, 30);
        deliveryFrame.add(saveButton);

        saveButton.addActionListener(e -> {
            if (cekDanSave(nameField.getText(),
                    alamatField.getText(),
                    noTelpField.getText(),
                    weightField.getText(),
                    categoryDeliveryComboBox.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(deliveryFrame, "Transaksi berhasil disimpan!");
                deliveryFrame.dispose();
                new AddDeliveryDetail();
            }
        });

        JButton detailsButton = new JButton("Add Details");
        detailsButton.setBounds(150, 320, 200, 30);
        deliveryFrame.add(detailsButton);

        detailsButton.addActionListener(e -> {
            new AddDeliveryDetail();
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 360, 200, 30);
        deliveryFrame.add(backButton);

        backButton.addActionListener(e -> {
            deliveryFrame.dispose();
            new MenuView();
        });

        deliveryFrame.setVisible(true);
    }

    private boolean cekDanSave(String name, String address, String phone, String weightStr, String category) {
        CurrentCustomer currentCustomer = CurrentCustomer.getInstance();
        if (!currentCustomer.isLoggedIn()) {
            JOptionPane.showMessageDialog(null, "Tidak ada customer yang sedang login!");
            return false;
        }

        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || weightStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua data harus diisi!");
            return false;
        }

        double weight;
        try {
            weight = Double.parseDouble(weightStr);
            if (weight <= 0) {
                JOptionPane.showMessageDialog(null, "Berat tidak boleh 0 (nol)");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Berat harus dalam bentuk angka!");
            return false;
        }

        int categoryIndex = categoryDeliveryComboBox.getSelectedIndex();
        double fee = fees.get(categoryIndex);
        double totalCost = weight * fee;

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            String sql = "INSERT INTO transactions (id_customer, delivery_type, expected_weight, total_cost, created_at, " +
                        "receipt_name, receipt_address, receipt_phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.con.prepareStatement(sql);
            pstmt.setInt(1, currentCustomer.getUser().getId_customer());  // Updated to getId_customer()
            pstmt.setString(2, category);
            pstmt.setDouble(3, weight);
            pstmt.setDouble(4, totalCost);
            pstmt.setString(5, timestamp);
            pstmt.setString(6, name);
            pstmt.setString(7, address);
            pstmt.setString(8, phone);
    
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error menyimpan transaksi: " + e.getMessage());
            return false;
        }
    }
}