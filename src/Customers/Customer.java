package Customers;

import Auth.User;

public class Customer extends User {
    private String password;

    public Customer(int customer_id, String name, String email, String password){
        super(customer_id, name, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void showUserType() {
        System.out.println("👤 Detta är en kund.");
    }

    public int getCustomer_id() {
        return id;
    }

    public void setCustomer_id(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
