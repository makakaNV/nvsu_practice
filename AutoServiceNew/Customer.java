package AutoServiceNew;

public class Customer {
    private String fullName;
    private String phoneNumber;
    private String vin;

    public Customer(String fullName, String phoneNumber, String vin) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.vin = vin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", vin='" + vin + '\'' +
                '}';
    }
}
