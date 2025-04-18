package Auth;

import Customers.Customer;

public class SessionManager {
    private static Customer loggedInCustomer;


    public static void login(Customer customer){
        loggedInCustomer = customer;
    }


    public static void logout() {
        loggedInCustomer = null;
    }

    public static Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public static boolean isLoggedIn() {
        return loggedInCustomer != null;
    }



}

