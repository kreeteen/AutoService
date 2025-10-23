package autoservice.domain.inmemory;

import autoservice.domain.model.OrderEmployee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryOrderEmployeeRepository implements autoservice.domain.repository.OrderEmployeeRepository {
    private final List<OrderEmployee> orderEmployees = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public OrderEmployee save(OrderEmployee orderEmployee) {
        orderEmployees.removeIf(oe ->
                oe.getEmployeeId().equals(orderEmployee.getEmployeeId()) &&
                        oe.getOrderId().equals(orderEmployee.getOrderId()));
        orderEmployees.add(orderEmployee);
        return orderEmployee;
    }

    @Override
    public List<OrderEmployee> findAll() {
        return new ArrayList<>(orderEmployees);
    }

    @Override
    public List<OrderEmployee> findByOrderId(Long orderId) {
        return orderEmployees.stream()
                .filter(oe -> orderId.equals(oe.getOrderId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderEmployee> findByEmployeeId(Long employeeId) {
        return orderEmployees.stream()
                .filter(oe -> employeeId.equals(oe.getEmployeeId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        orderEmployees.removeIf(oe -> orderId.equals(oe.getOrderId()));
    }
}
