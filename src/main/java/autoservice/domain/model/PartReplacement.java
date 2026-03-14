package autoservice.domain.model;

public class PartReplacement {
    private Long replacementId;
    private Long orderId;
    private String partName;
    private String partNumber;
    private Integer quantity;

    public PartReplacement(Long replacementId,
                           Long orderId,
                           String partName,
                           String partNumber,
                           int quantity) {
        this.replacementId = replacementId;
        this.orderId = orderId;
        this.partName = partName;
        this.partNumber = partNumber;
        this.quantity = (Integer) quantity;
    }

    public Long getReplacementId() { return replacementId; }
    public void setReplacementId(Long replacementId) { this.replacementId = replacementId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getPartName() { return partName; }
    public void setPartName(String partName) { this.partName = partName; }
    public String getPartNumber() { return partNumber; }
    public void setPartNumber(String partNumber) { this.partNumber = partNumber; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
