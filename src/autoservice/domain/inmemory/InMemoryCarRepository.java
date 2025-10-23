package autoservice.domain.inmemory;

import autoservice.domain.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class InMemoryCarRepository implements autoservice.domain.repository.CarRepository {
    private final Map<Long, Car> cars = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Car save(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(idCounter.getAndIncrement());
        }
        cars.put(car.getCarId(), car);
        return car;
    }

    @Override
    public Optional<Car> findById(Long id) {
        return Optional.ofNullable(cars.get(id));
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(cars.values());
    }

    @Override
    public void deleteById(Long id) {
        cars.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cars.containsKey(id);
    }

    @Override
    public List<Car> findByClientId(Long clientId) {
        return cars.values().stream()
                .filter(car -> clientId.equals(car.getClientId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findByLicensePlate(String licensePlate) {
        return cars.values().stream()
                .filter(car -> licensePlate.equalsIgnoreCase(car.getLicensePlate()))
                .collect(Collectors.toList());
    }
}
