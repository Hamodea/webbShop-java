package Auth;

public abstract class User {
    protected int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String getDisplayName();  // t.ex. "Mohmad" eller "admin123"
    public abstract void showUserType();
}
