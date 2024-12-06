package AutoServiceNew;

import java.util.List;

public interface RepairOrderDAO {
    void addRepairOrder(RepairOrder order);
    RepairOrder getRepairOrderById(int orderId);
    List<RepairOrder> getAllRepairOrders();
    void updateRepairOrder(RepairOrder order);
    void deleteRepairOrder(int orderId);
}

