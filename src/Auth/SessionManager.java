package Auth;



public class SessionManager {
    private static SessionManager instance;
    private User loggedInUser;

    // Privat konstruktor förhindrar att skapa nya instanser utifrån
    private SessionManager() {}

    // Endast denna metod ger dig instansen
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        this.loggedInUser = user;
    }

    public void logout() {
        loggedInUser = null;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
