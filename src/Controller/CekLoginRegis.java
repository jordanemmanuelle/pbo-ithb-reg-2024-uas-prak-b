package Controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.*;

public class CekLoginRegis {

    static DatabaseHandler connect = new DatabaseHandler();

    public static Customer cekLogin(String phone, String password) {
        try {
            connect.connect();
            String QUERY = "SELECT * FROM customers WHERE phone = ? AND password = ?";
            PreparedStatement stmt = connect.con.prepareStatement(QUERY);
            stmt.setString(1, phone);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer loggedInUser = new Customer(rs.getInt("id_customer"), rs.getString("password"), rs.getString("name"),
                        rs.getString("address"), rs.getString("phone"));

                if (loggedInUser != null) {
                    CurrentCustomer.getInstance().setUser(loggedInUser);
                }

                return loggedInUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean cekRegis(String phone, String address, String name, String password) {
        try {
            connect.connect();
            String query = "SELECT * FROM customers WHERE phone = ? AND address = ?";
            PreparedStatement stmt = connect.con.prepareStatement(query);
            stmt.setString(1, phone);
            stmt.setString(2, address);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            String QUERY = "INSERT INTO customers (name, address, phone, password) VALUES (?, ?, ?, ?)";
            PreparedStatement insertStmt = connect.con.prepareStatement(QUERY);
            insertStmt.setString(1, name);
            insertStmt.setString(2, address);
            insertStmt.setString(3, phone);
            insertStmt.setString(4, password);

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}