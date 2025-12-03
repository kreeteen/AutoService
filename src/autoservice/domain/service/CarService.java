package autoservice.domain.service;

import autoservice.domain.model.Car;
import autoservice.domain.repository.CarRepository;
import autoservice.domain.util.LicensePlateValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car createCar(Long clientId, String licensePlate, String brand,
                         String model, LocalDate manufactureDate) {
        String formattedPlate = validationLicensePlate(licensePlate);

        Car car = new Car(null, clientId, formattedPlate, brand, model, manufactureDate);
        return carRepository.save(car);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getCarsByClientId(Long clientId) {
        return carRepository.findByClientId(clientId);
    }

    public List<Car> getCarsByLicensePlate(String licensePlate) {
        String formattedPlate = LicensePlateValidator.formatLicensePlate(licensePlate);
        return carRepository.findByLicensePlate(formattedPlate);
    }

    public boolean updateCarLicensePlate(Long id, String newLicensePlate) {
        String formattedPlate = validationLicensePlate(newLicensePlate);

        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
            Car existingCar = car.get();
            existingCar.setLicensePlate(formattedPlate);
            carRepository.save(existingCar);
            return true;
        }
        return false;
    }

    public boolean deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private boolean isLicensePlateExists(String licensePlate) {
        List<Car> carsWithSamePlate = carRepository.findByLicensePlate(licensePlate);
        return !carsWithSamePlate.isEmpty();
    }

    private String validationLicensePlate(String licensePlate) {
        // Validation of new LicensePlate
        if (!LicensePlateValidator.isValidRussianLicensePlate(licensePlate)) {
            throw new IllegalArgumentException("Неверный формат номерного знака: " + licensePlate);
        }

        // Checking of new unique LicensePlate
        String formattedPlate = LicensePlateValidator.formatLicensePlate(licensePlate);
        if (isLicensePlateExists(formattedPlate)) {
            throw new IllegalArgumentException("Автомобиль с номерным знаком " + formattedPlate + " уже существует");
        }
        return formattedPlate;
    }
}