package Model;

public class Transaction {
    private int id_transaction;
    private int id_customer;
    private String delivery_type;
    private double expected_weight;
    private double total_cost;
    private String created_at;
    private String receipt_name;
    private String receipt_address;
    private String receipt_phone;

    public Transaction(int id_transaction, int id_customer, String delivery_type, double expected_weight,
            double total_cost, String created_at, String receipt_name, String receipt_address, String receipt_phone) {
        this.id_transaction = id_transaction;
        this.id_customer = id_customer;
        this.delivery_type = delivery_type;
        this.expected_weight = expected_weight;
        this.total_cost = total_cost;
        this.created_at = created_at;
        this.receipt_name = receipt_name;
        this.receipt_address = receipt_address;
        this.receipt_phone = receipt_phone;
    }
    
    public int getId_transaction() {
        return id_transaction;
    }
    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }
    public int getId_customer() {
        return id_customer;
    }
    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }
    public String getDelivery_type() {
        return delivery_type;
    }
    public void setDelivery_type(String delivery_type) {
        this.delivery_type = delivery_type;
    }
    public double getExpected_weight() {
        return expected_weight;
    }
    public void setExpected_weight(double expected_weight) {
        this.expected_weight = expected_weight;
    }
    public double getTotal_cost() {
        return total_cost;
    }
    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getReceipt_name() {
        return receipt_name;
    }
    public void setReceipt_name(String receipt_name) {
        this.receipt_name = receipt_name;
    }
    public String getReceipt_address() {
        return receipt_address;
    }
    public void setReceipt_address(String receipt_address) {
        this.receipt_address = receipt_address;
    }
    public String getReceipt_phone() {
        return receipt_phone;
    }
    public void setReceipt_phone(String receipt_phone) {
        this.receipt_phone = receipt_phone;
    }

}
