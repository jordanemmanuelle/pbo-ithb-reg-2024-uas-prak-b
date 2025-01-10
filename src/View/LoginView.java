package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.*;
import Model.Customer;

public class LoginView {
    public LoginView(){
        ShowLoginView();
    }

    public void ShowLoginView(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JFrame loginFrame = new JFrame("Login");
        loginFrame.setLayout(null);
        loginFrame.setSize(400, 400);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = (screenSize.width - loginFrame.getWidth()) / 2;
        int y = (screenSize.height - loginFrame.getHeight()) / 2;
        loginFrame.setLocation(x, y);



        // 
        JLabel title = new JLabel("Login");
        title.setBounds(150, 20, 100, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginFrame.add(title);

        JLabel noTelpLabel = new JLabel("No. Telp:");
        noTelpLabel.setBounds(50, 80, 100, 25);
        loginFrame.add(noTelpLabel);

        JTextField noTelpField = new JTextField();
        noTelpField.setBounds(150, 80, 200, 25);
        loginFrame.add(noTelpField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 120, 100, 25);
        loginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 120, 200, 25);
        loginFrame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 160, 200, 30);
        loginFrame.add(loginButton);

        loginButton.addActionListener(e -> {
            String nomorTelepon = noTelpField.getText();
            String password = new String(passwordField.getPassword());

            if (!nomorTelepon.isEmpty() && !password.isEmpty()) {
                Customer verifying = CekLoginRegis.cekLogin(nomorTelepon, password);
                if (verifying == null) {
                    JOptionPane.showMessageDialog(loginFrame, "Pastikan Username/Password Anda benar!");
                } else {
                    loginFrame.dispose();
                    new MenuView(); // 
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Username/Password harus diisi!");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 200, 200, 30);
        loginFrame.add(backButton);

        backButton.addActionListener(e -> {
            loginFrame.dispose();
            new MenuView();
        });
        
        loginFrame.setVisible(true);
    }
}