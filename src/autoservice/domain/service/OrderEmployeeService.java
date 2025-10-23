package autoservice.domain.service;

import autoservice.domain.model.OrderEmployee;
import autoservice.domain.repository.OrderEmployeeRepository;

import java.util.List;

public class OrderEmployeeService {
    private final OrderEmployeeRepository orderEmployeeRepository;

    public OrderEmployeeService(OrderEmployeeRepository orderEmployeeRepository) {
        this.orderEmployeeRepository = orderEmployeeRepository;
    }

    public OrderEmployee assignEmployeeToOrder(Long employeeId, Long orderId, String role) {
        OrderEmployee orderEmployee = new OrderEmployee(employeeId, orderId, role);
        return orderEmployeeRepository.save(orderEmployee);
    }

    public List<OrderEmployee> getEmployeesByOrder(Long orderId) {
        return orderEmployeeRepository.findByOrderId(orderId);
    }

    public List<OrderEmployee> getOrdersByEmployee(Long employeeId) {
        return orderEmployeeRepository.findByEmployeeId(employeeId);
    }

    public void removeEmployeesFromOrder(Long orderId) {
        orderEmployeeRepository.deleteByOrderId(orderId);
    }
}