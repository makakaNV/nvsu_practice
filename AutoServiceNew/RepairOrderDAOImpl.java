package AutoServiceNew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairOrderDAOImpl implements RepairOrderDAO {
    private Connection connection;

    public RepairOrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addRepairOrder(RepairOrder order) {
        String sql = "INSERT INTO repair_orders (order_id, vin, description, status, creation_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.setString(2, order.getVin());
            stmt.setString(3, order.getDescription());
            stmt.setString(4, order.getStatus());
            stmt.setDate(5, Date.valueOf(order.getCreationDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RepairOrder getRepairOrderById(int orderId) {
        String sql = "SELECT * FROM repair_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RepairOrder(
                        rs.getInt("order_id"),
                        rs.getString("vin"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RepairOrder getRepairOrderByVin(String vin) {
        String sql = "SELECT * FROM repair_orders WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RepairOrder(
                        rs.getInt("order_id"),
                        rs.getString("vin"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getOrderIdByVin(String vin) {
        String sql = "SELECT order_id FROM repair_orders WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("order_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getVinByOrderId(int orderId) {
        String sql = "SELECT vin FROM repair_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("vin");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RepairOrder> getRepairOrderByStatus(String status) {
        String sql = "SELECT * FROM repair_orders WHERE status = ?";
        List<RepairOrder> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(new RepairOrder(
                        rs.getInt("order_id"),
                        rs.getString("vin"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    @Override
    public List<RepairOrder> getAllRepairOrders() {
        String sql = "SELECT * FROM repair_orders";
        List<RepairOrder> orders = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                orders.add(new RepairOrder(
                        rs.getInt("order_id"),
                        rs.getString("vin"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getDate("creation_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void updateRepairOrder(RepairOrder order) {
        String sql = "UPDATE repair_orders SET vin = ?, description = ?, status = ?, creation_date = ? WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, order.getVin());
            stmt.setString(2, order.getDescription());
            stmt.setString(3, order.getStatus());
            stmt.setDate(4, Date.valueOf(order.getCreationDate()));
            stmt.setInt(5, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRepairOrder(int orderId) {
        String sql = "DELETE FROM repair_orders WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateRepairOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE repair_orders SET status = ? WHERE order_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
