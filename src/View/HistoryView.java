package View;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;
import Controller.DatabaseHandler;

public class HistoryView {
    private JFrame frame;
    private JTable historyTable;
    private DatabaseHandler conn;
    
    public HistoryView() {
        try {
            conn = new DatabaseHandler();
            conn.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal: " + e.getMessage());
        }
        tampilkanHistoryView();
    }

    private void tampilkanHistoryView() {
        frame = new JFrame("History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout(10, 10));

        // Buat model tabel
        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1; 
            }
        };

        // Definisikan kolom tabel
        tableModel.addColumn("ID Transaksi");
        tableModel.addColumn("Jenis Pengiriman");
        tableModel.addColumn("Berat Paket");
        tableModel.addColumn("Total Biaya");
        tableModel.addColumn("Tanggal Dibuat");
        tableModel.addColumn("Terakhir Update");
        tableModel.addColumn("Aksi");

        historyTable = new JTable(tableModel);
        
        // Atur renderer khusus untuk kolom button
        historyTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        historyTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Tambahkan tabel ke scroll pane
        JScrollPane scrollPane = new JScrollPane(historyTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Tombol Kembali
        JButton backButton = new JButton("Kembali");
        backButton.addActionListener(e -> {
            frame.dispose();
            new MenuView();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Muat data
        muatDataHistory();

        // Atur lebar kolom
        historyTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID Transaksi
        historyTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Jenis Pengiriman
        historyTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Berat Paket
        historyTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Total Biaya
        historyTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Tanggal Dibuat
        historyTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Terakhir Update
        historyTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Tombol

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void muatDataHistory() {
        try {
            String sql = """
                SELECT 
                    CONCAT('PER-TX', LPAD(t.id_transaction, 4, '0')) as formatted_id,
                    t.delivery_type,
                    t.expected_weight,
                    t.total_cost,
                    t.created_at,
                    (SELECT MAX(date) 
                     FROM delivery_details dd 
                     WHERE dd.id_transaction = t.id_transaction) as last_updated
                FROM transactions t
                ORDER BY t.created_at DESC
            """;

            Statement stmt = conn.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
            model.setRowCount(0); // Bersihkan data yang ada

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("formatted_id"),
                    rs.getString("delivery_type"),
                    rs.getDouble("expected_weight") + " kg",
                    "Rp " + String.format("%,.2f", rs.getDouble("total_cost")),
                    rs.getString("created_at"),
                    rs.getString("last_updated"),
                    "Lihat Detail"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error memuat riwayat: " + e.getMessage());
        }
    }
}

// Renderer khusus untuk tombol
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

// Editor khusus untuk tombol
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        
        button.addActionListener(e -> {
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Dapatkan ID transaksi dari kolom pertama
            JTable table = ((JTable)button.getParent());
            String transactionId = (String)table.getValueAt(table.getSelectedRow(), 0);
            
            // Buka tampilan detail
            SwingUtilities.getWindowAncestor(button).dispose();
            new AddDeliveryDetail();
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}