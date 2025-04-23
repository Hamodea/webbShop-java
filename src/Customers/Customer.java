package Customers;

import Auth.User;

public class Customer extends User {
    private final String name;
    private final String email;
    private final String password;

    public Customer(int id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    public void showUserType() {
        System.out.println("👤 Detta är en kund.");
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
