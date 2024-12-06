package AutoServiceNew;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    Customer getCustomerByVin(String vin);
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer);
    void deleteCustomer(String vin);
}
