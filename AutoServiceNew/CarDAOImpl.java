package AutoServiceNew;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {
    private Connection connection;

    public CarDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addCar(Car car) {
        String sqlCheck = "SELECT COUNT(*) FROM cars WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlCheck)) {
            stmt.setString(1, car.getVin());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("Автомобиль с таким VIN уже существует.");
                return;
            }

            String sql = "INSERT INTO cars (vin, brand, model, year) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
                insertStmt.setString(1, car.getVin());
                insertStmt.setString(2, car.getBrand());
                insertStmt.setString(3, car.getModel());
                insertStmt.setInt(4, car.getYear());
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Car getCarByVIN(String vin) {
        String sql = "SELECT * FROM cars WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vin);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Car(
                        rs.getString("vin"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Car> getAllCars() {
        String sql = "SELECT * FROM cars";
        List<Car> cars = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                cars.add(new Car(
                        rs.getString("vin"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void updateCar(Car car) {
        String sql = "UPDATE cars SET brand = ?, model = ?, year = ? WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getYear());
            stmt.setString(4, car.getVin());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(String vin) {
        String sql = "DELETE FROM cars WHERE vin = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vin);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


