package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.*;
import Controller.*;
import Model.DeliveryStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddDeliveryDetail {
    private DatabaseHandler conn;
    private JFrame detailFrame;
    private JTextField IDTransaksiField;
    private JComboBox<DeliveryStatus> statusComboBox;
    private JTextField positionField;
    private JTextField evidenceField;
    private JTextField updatedByField;

    public AddDeliveryDetail() {
        try {
            conn = new DatabaseHandler();
            conn.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal" + e.getMessage(), null, 0);
        }
        showDetailView();
    }

    public void showDetailView() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        detailFrame = new JFrame("Add Delivery Detail");
        detailFrame.setLayout(null);
        detailFrame.setSize(400, 500);
        detailFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = (screenSize.width - detailFrame.getWidth()) / 2;
        int y = (screenSize.height - detailFrame.getHeight()) / 2;
        detailFrame.setLocation(x, y);

        JLabel title = new JLabel("Add Delivery Detail");
        title.setBounds(80, 20, 350, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        detailFrame.add(title);

        //
        JLabel IDTransaksiLabel = new JLabel("ID Transaksi:");
        IDTransaksiLabel.setBounds(50, 80, 100, 25);
        detailFrame.add(IDTransaksiLabel);

        IDTransaksiField = new JTextField();
        IDTransaksiField.setBounds(150, 80, 200, 25);
        detailFrame.add(IDTransaksiField);

        // 
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 120, 100, 25);
        detailFrame.add(statusLabel);

        statusComboBox = new JComboBox<>(DeliveryStatus.values());
        statusComboBox.setBounds(150, 120, 200, 25);
        detailFrame.add(statusComboBox);

        // 
        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setBounds(50, 160, 100, 25);
        detailFrame.add(positionLabel);

        positionField = new JTextField();
        positionField.setBounds(150, 160, 200, 25);
        detailFrame.add(positionField);

        // 
        JLabel evidenceLabel = new JLabel("Evidence:");
        evidenceLabel.setBounds(50, 200, 100, 25);
        detailFrame.add(evidenceLabel);

        evidenceField = new JTextField();
        evidenceField.setBounds(150, 200, 200, 25);
        detailFrame.add(evidenceField);

        // 
        JLabel updatedByLabel = new JLabel("Updated By:");
        updatedByLabel.setBounds(50, 240, 100, 25);
        detailFrame.add(updatedByLabel);

        updatedByField = new JTextField();
        updatedByField.setBounds(150, 240, 200, 25);
        detailFrame.add(updatedByField);

        // 
        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 300, 200, 30);
        detailFrame.add(saveButton);

        saveButton.addActionListener(e -> {
            cekDanSave();
        });

        // 
        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 340, 200, 30);
        detailFrame.add(backButton);
        
        backButton.addActionListener(e -> {
            detailFrame.dispose();
            new MenuView();
        });

        detailFrame.setVisible(true);
    }

    private void cekDanSave() {
        String id_transaksi = IDTransaksiField.getText();
        DeliveryStatus status = (DeliveryStatus) statusComboBox.getSelectedItem();
        String position = positionField.getText();
        String evidence = evidenceField.getText();
        String updatedBy = updatedByField.getText();

        if (id_transaksi.isEmpty() || position.isEmpty() || 
            evidence.isEmpty() || updatedBy.isEmpty()) {
            JOptionPane.showMessageDialog(detailFrame, "Semua data harus diisi!");
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try {
            if (!cekIDTransaksi(id_transaksi)) {
                JOptionPane.showMessageDialog(detailFrame, "ID Transaksi tidak valid!");
                return;
            }

            String sql = "INSERT INTO delivery_details (id_transaction, status, current_position, " +
                        "evidence, date, updated_by) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pstmt = conn.con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id_transaksi));
            pstmt.setString(2, status.toString());
            pstmt.setString(3, position);
            pstmt.setString(4, evidence);
            pstmt.setString(5, timestamp);
            pstmt.setString(6, updatedBy);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(detailFrame, "Detail data berhasil disimpan!");
                detailFrame.dispose();
                new MenuView();
            } else {
                JOptionPane.showMessageDialog(detailFrame, "Gagal menyimpan detail!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(detailFrame, "Error menyimpan detail: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(detailFrame, "ID Transaksi harus berupa angka!");
        }
    }

    private boolean cekIDTransaksi(String id_transaksi) {
        try {
            String sql = "SELECT id_transaction FROM transactions WHERE id_transaction = ?";
            PreparedStatement pstmt = conn.con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id_transaksi));
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if transaction ID exists
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(detailFrame, "Error validating transaction ID: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}