package Products;

import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {
    public static final String URL = "jdbc:sqlite:webbutiken1.db";

    public ArrayList<Products> getAll() throws SQLException {
        ArrayList<Products> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")){

            while (rs.next()){

                Products product = new Products(
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);

            }
        }
        return products;
    }

    public Products getProductByName(String name) throws SQLException {
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Products(
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
            } else {
                return null; // Om ingen matchande produkt hittas
            }
        }
    }

    public ArrayList<Products> getProductsByCategory(String categoryName) throws SQLException {
        ArrayList<Products> products = new ArrayList<>();
        String sql = """
                    SELECT p.*
                    FROM products p
                    JOIN products_categories pc ON p.product_id = pc.product_id
                    JOIN categories c ON pc.category_id = c.category_id
                    WHERE c.name LIKE ?
                   """;


        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + categoryName + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
                products.add(product);
            }
        }

        return products;
    }

    public Products getProductById(int productId) throws SQLException {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Products(
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity")
                );
            }
            return null;
        }
    }


    public boolean updateProductPrice(int productId, double newPrice) throws SQLException {
        String sql = "UPDATE products SET price = ? WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, productId);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("row har uppdatera" + affectedRows);
            return affectedRows > 0;
        }
    }

    public boolean updateproductStock(int productId, int newStock) throws SQLException{
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

        try(Connection conn = DriverManager.getConnection(URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, productId);

            int affectedRow = pstmt.executeUpdate();
            return affectedRow > 0;
        }
    }

    public Products addNewProduct( int manufacturer_id, String name, String description, double price, int stock_quantity) throws SQLException{
        String sql = "INSERT INTO products(manufacturer_id, name, description, price , stock_quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, manufacturer_id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            pstmt.setDouble(4, price);
            pstmt.setInt(5, stock_quantity);

            System.out.println("DEBUG: stock_quantity = " + stock_quantity);


            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                ResultSet keys = pstmt.getGeneratedKeys();
                if (keys.next()) {
                    int generatedId = keys.getInt(1); //  Det nya produkt-ID:t från databasen
                    return new Products(generatedId, manufacturer_id, name, description, price, stock_quantity);
                }
            }

            return null;
        }



    }






}
