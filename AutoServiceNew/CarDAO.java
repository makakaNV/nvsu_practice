package AutoServiceNew;

import java.util.List;

public interface CarDAO {
    void addCar(Car car);
    Car getCarByVIN(String vin);
    List<Car> getAllCars();
    void updateCar(Car car);
    void deleteCar(String vin);
}

