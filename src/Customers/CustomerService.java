package Customers;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService {
    CustomerRepository customerRepository = new CustomerRepository();
    public  ArrayList<Customer> getAllcustomers() throws SQLException {
        return customerRepository.getAll();
    }

    public Customer getUserId(int customer_id) throws SQLException{
        return customerRepository.getCustomerById(customer_id);
    }

    public Customer addCustomer( String name, String email, String password) throws SQLException {
        return customerRepository.addUser(name, email, password);
    }
    public boolean updateEmail(int customer_id, String email) throws SQLException {
        return customerRepository.updateEmail(customer_id, email);

    }
    public boolean deleteUser(int customer_id) throws SQLException{
        return customerRepository.deleteCustomer(customer_id);
    }

}
