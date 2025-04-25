package Products;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProductService {

    ProductRepository productRepository = new ProductRepository();
    public ArrayList<Products> getAllProducts() throws SQLException{
        return productRepository.getAll();
    }

    public Products findProductByName(String name) throws SQLException{
        return productRepository.getProductByName(name);
    }

    public ArrayList<Products> findProductByCategory(String categoryName) throws SQLException{
        return productRepository.getProductsByCategory(categoryName);
    }

    public Products getProduct(int productId) throws SQLException {
        return productRepository.getProductById(productId);
    }


    public boolean updatePrice(int productId, double newPrice) throws SQLException {
        return productRepository.updateProductPrice(productId, newPrice);
    }

    public boolean updateStock(int newStock, int productId) throws SQLException{
        return  productRepository.updateproductStock(newStock, productId);
    }

    public Products addProduct( int manufacturer_id, String name, String description, double price, int stock_quantity) throws SQLException{
        return  productRepository.addNewProduct(manufacturer_id, name, description, price, stock_quantity);
    }

    public ArrayList<Products> getProductsByMaxPrice(double maxPrice) throws SQLException {
        ArrayList<Products> allProducts = productRepository.getAll(); // hämta alla produkter
        return allProducts.stream()
                .filter(p -> p.getPrice() <= maxPrice)
                .collect(Collectors.toCollection(ArrayList::new)); // filtrera listan
    }



}
