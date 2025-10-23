package autoservice.domain.model;

public class OrderEmployee {
    private Long employeeId;
    private Long orderId;
    private String roleInOrder;

    public OrderEmployee(Long employeeId, Long orderId, String roleInOrder) {
        this.employeeId = employeeId;
        this.orderId = orderId;
        this.roleInOrder = roleInOrder;
    }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getRoleInOrder() { return roleInOrder; }
    public void setRoleInOrder(String roleInOrder) { this.roleInOrder = roleInOrder; }
}
