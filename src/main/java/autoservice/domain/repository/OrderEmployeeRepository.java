package autoservice.domain.repository;

import autoservice.domain.model.OrderEmployee;

import java.util.List;

public interface OrderEmployeeRepository {
    OrderEmployee save(OrderEmployee orderEmployee);
    List<OrderEmployee> findAll();
    List<OrderEmployee> findByOrderId(Long orderId);
    List<OrderEmployee> findByEmployeeId(Long employeeId);
    void deleteByOrderId(Long orderId);
}
