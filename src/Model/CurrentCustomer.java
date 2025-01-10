package Model;

public class CurrentCustomer {
    private static CurrentCustomer instance;
    private Customer user;

    private CurrentCustomer() {}

    public static CurrentCustomer getInstance() {
        if (instance == null) {
            instance = new CurrentCustomer();
        }
        return instance;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public void clearUser() {
        this.user = null;
    }

    public boolean isLoggedIn() {
        return this.user != null;
    }
}