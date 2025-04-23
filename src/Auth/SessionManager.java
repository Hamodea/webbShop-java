package Auth;


public class SessionManager {
    private static User loggedInUser;

    public static void login(User user) {
        loggedInUser = user;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
