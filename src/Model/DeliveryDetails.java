package Model;

import java.sql.Date;

public class DeliveryDetails {
    private int id_delivery_details;
    private int id_transaction;
    private DeliveryStatus status;
    private String current_position;
    private String evidence; // url gambar
    private Date date;
    private String updated_by;
    
    public DeliveryDetails(int id_delivery_details, int id_transaction, DeliveryStatus status, String current_position,
            String evidence, Date date, String updated_by) {
        this.id_delivery_details = id_delivery_details;
        this.id_transaction = id_transaction;
        this.status = status;
        this.current_position = current_position;
        this.evidence = evidence;
        this.date = date;
        this.updated_by = updated_by;
    }
    
    public int getId_delivery_details() {
        return id_delivery_details;
    }

    public void setId_delivery_details(int id_delivery_details) {
        this.id_delivery_details = id_delivery_details;
    }

    public int getId_transaction() {
        return id_transaction;
    }

    public void setId_transaction(int id_transaction) {
        this.id_transaction = id_transaction;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public String getCurrent_position() {
        return current_position;
    }

    public void setCurrent_position(String current_position) {
        this.current_position = current_position;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }


}
