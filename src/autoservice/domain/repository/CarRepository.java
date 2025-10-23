package autoservice.domain.repository;

import autoservice.domain.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Car save(Car car);
    Optional<Car> findById(Long id);
    List<Car> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<Car> findByClientId(Long clientId);
    List<Car> findByLicensePlate(String licensePlate);
}
