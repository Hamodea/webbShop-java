package Auth;

public abstract class User {
    protected int id;

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract String getDisplayName();  //

    public abstract String showUserType();
}
