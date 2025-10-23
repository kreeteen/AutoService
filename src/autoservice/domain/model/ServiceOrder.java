package autoservice.domain.model;
import java.time.LocalDate;

public class ServiceOrder {
    private Long orderId;
    private Long carId;
    private LocalDate createdDate;
    private String description;
    private String repairDescription;
    private LocalDate completionDate;
    private String status;

    public ServiceOrder(Long orderId, Long carId, LocalDate createdDate, String description,
                        String repairDescription, LocalDate completionDate, String status) {
        this.orderId = orderId;
        this.carId = carId;
        this.createdDate = createdDate;
        this.description = description;
        this.repairDescription = repairDescription;
        this.completionDate = completionDate;
        this.status = status;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getRepairDescription() { return repairDescription; }
    public void setRepairDescription(String repairDescription) { this.repairDescription = repairDescription; }
    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
