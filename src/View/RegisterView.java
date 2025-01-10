package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Controller.*;

public class RegisterView {
    public RegisterView() {
        showRegisterView();
    }

    public static void showRegisterView() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JFrame regisFrame = new JFrame("Registration");
        regisFrame.setLayout(null);
        regisFrame.setSize(400, 400);
        regisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = (screenSize.width - regisFrame.getWidth()) / 2;
        int y = (screenSize.height - regisFrame.getHeight()) / 2;
        regisFrame.setLocation(x, y);


        JLabel title = new JLabel("Registration");
        title.setBounds(130, 20, 350, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        regisFrame.add(title);

        //
        JLabel noTelpLabel = new JLabel("No. Telp:");
        noTelpLabel.setBounds(50, 80, 100, 25);
        regisFrame.add(noTelpLabel);

        JTextField noTelpField = new JTextField();
        noTelpField.setBounds(150, 80, 200, 25);
        regisFrame.add(noTelpField);

        //
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 25);
        regisFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 120, 200, 25);
        regisFrame.add(nameField);

        //
        JLabel alamatLabel = new JLabel("Address:");
        alamatLabel.setBounds(50, 160, 100, 25);
        regisFrame.add(alamatLabel);

        JTextField alamatField = new JTextField();
        alamatField.setBounds(150, 160, 200, 25);
        regisFrame.add(alamatField);

        //
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 200, 100, 25);
        regisFrame.add(passwordLabel);

        JTextField passwordField = new JTextField();
        passwordField.setBounds(150, 200, 200, 25);
        regisFrame.add(passwordField);
        
        //
        JButton regisButton = new JButton("Register");
        regisButton.setBounds(150, 240, 200, 30);
        regisFrame.add(regisButton);

        
        regisButton.addActionListener(e -> {
            String nomorTelepon = noTelpField.getText();
            String nama = nameField.getText();
            String alamat = alamatField.getText();
            String password = passwordField.getText();
            
            if (!nomorTelepon.isEmpty() && !password.isEmpty() && !nama.isEmpty() && !alamat.isEmpty()) {
                boolean registing = CekLoginRegis.cekRegis(nomorTelepon, alamat, nama, password);
                if (!registing) {
                    JOptionPane.showMessageDialog(regisFrame, "No. Telp sudah terdaftar!");
                } else {
                    JOptionPane.showMessageDialog(regisFrame, "Berhasil melakukan registrasi!");
                    regisFrame.dispose();
                    new MenuView();
                }
            } else {
                JOptionPane.showMessageDialog(regisFrame, "Data harus diisi!");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 280,200, 30);
        regisFrame.add(backButton);
        
        backButton.addActionListener(e -> {
            regisFrame.dispose();
            new MenuView();
        });
        
        regisFrame.setVisible(true);
    }
}