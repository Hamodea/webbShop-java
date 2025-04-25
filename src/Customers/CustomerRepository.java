package Customers;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public static final String URL = "jdbc:sqlite:webbutiken1.db";

    public ArrayList<Customer> getAll() throws SQLException {
       ArrayList<Customer> customers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {

                Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"));
                customers.add(customer);
            }
        }
        return customers;
    }
    public Customer getCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            customerId,
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                } else {
                    return null; // Ingen kund med detta ID
                }
            }
        }
    }

    public Customer addUser(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO customers(customer_id, name, email, password) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    return new Customer(newId, name, email, password);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }
    public boolean updateEmail(int customerId, String newEmail) throws SQLException {
        String sql = "UPDATE customers SET email = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, customerId);

            int rowUpdate = pstmt.executeUpdate();
            return rowUpdate > 0;
        }

    }

    public boolean deleteCustomer(int customer_id) throws SQLException{
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customer_id);

            int rowDeleted = pstmt.executeUpdate();
            return rowDeleted > 0;

        }

    }

}