package AutoServiceNew;

import java.time.LocalDate;

public class RepairOrder {
    private int orderId;
    private String vin;
    private String description;
    private String status; // "New", "In Progress", "Completed"
    private LocalDate creationDate;

    public RepairOrder(int orderId, String vin, String description, String status, LocalDate creationDate) {
        this.orderId = orderId;
        this.vin = vin;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "orderId='" + orderId + '\'' +
                ", vin='" + vin + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
