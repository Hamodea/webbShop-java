package Products;

public class Products {
    private int product_id;
    private int manufacturer_id;
    private String name;
    private String description;
    private double price;
    private int stock_quantity;

    public Products(int product_id, int manufacturer_id, String name, String description, double price, int stock_quantity ){
        this.product_id = product_id;
        this.manufacturer_id = manufacturer_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock_quantity = stock_quantity;
    }


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock_quantity;
    }

    public void setStock(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
}
