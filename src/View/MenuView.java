package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

public class MenuView {

    public MenuView() {
        ShowMenu();
    }

    public void ShowMenu() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        JFrame menu = new JFrame("Pratama Delivery Apps");
        menu.setSize(500, 500);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int x = (screenSize.width - menu.getWidth()) / 2;
        int y = (screenSize.height - menu.getHeight()) / 2;
        menu.setLocation(x, y);

        JLabel label = new JLabel(" Welcome to Pratama Delivery");
        label.setFont(new Font ("SansSerif", Font.BOLD, 25));
        label.setBounds(70, 60, 400, 30);
        menu.add(label);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(150, 150, 200, 40);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        loginButton.setBackground(new Color(31, 31, 31));
        loginButton.setForeground(Color.WHITE);
        menu.add(loginButton);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBounds(150, 210, 200, 40);
        registerButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        registerButton.setBackground(new Color(31, 31, 31));
        registerButton.setForeground(Color.WHITE);
        menu.add(registerButton);

        JButton addTransactionButton = new JButton("ADD TRANSACTION");
        addTransactionButton.setBounds(150, 270, 200, 40);
        addTransactionButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        addTransactionButton.setBackground(new Color(31, 31, 31));
        addTransactionButton.setForeground(Color.WHITE);
        menu.add(addTransactionButton);

        JButton historyButton = new JButton("HISTORY");
        historyButton.setBounds(150, 330, 200, 40);
        historyButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        historyButton.setBackground(new Color(31, 31, 31));
        historyButton.setForeground(Color.WHITE);
        menu.add(historyButton);

        JButton exitButton = new JButton("EXIT");
        exitButton.setBounds(150, 390, 200, 40);
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        exitButton.setBackground(new Color(31, 31, 31));
        exitButton.setForeground(Color.WHITE);
        menu.add(exitButton);

        loginButton.addActionListener(e -> {
            menu.dispose();
            new LoginView();
        });

        registerButton.addActionListener(e -> {
            menu.dispose();
            new RegisterView();
        });

        exitButton.addActionListener(e -> {
            menu.dispose();
        });

        addTransactionButton.addActionListener(e -> {
            menu.dispose();
            new AddDeliveryTransaction();
        });

        historyButton.addActionListener(e -> {
            menu.dispose();
            new HistoryView();
        });

        menu.setVisible(true);
    }
}