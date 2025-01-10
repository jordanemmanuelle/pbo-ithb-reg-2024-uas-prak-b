package Model;

public class Customer {
    private int id_customer;
    private String password;
    private String name;
    private String address;
    private String phone;

    public Customer(int id_customer, String password, String name, String address, String phone) {
        this.id_customer = id_customer;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}