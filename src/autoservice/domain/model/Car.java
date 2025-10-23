package autoservice.domain.model;
import java.time.LocalDate;

public class Car {
    private Long carId;
    private Long clientId;
    private String licensePlate;
    private String brand;
    private String model;
    private LocalDate manufactureDate;

    public Car(Long carId, Long clientId, String licensePlate, String brand,
               String model, LocalDate manufactureDate) {
        this.carId = carId;
        this.clientId = clientId;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.manufactureDate = manufactureDate;
    }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public LocalDate getManufactureDate() { return manufactureDate; }
    public void setManufactureDate(LocalDate manufactureDate) { this.manufactureDate = manufactureDate; }
}
