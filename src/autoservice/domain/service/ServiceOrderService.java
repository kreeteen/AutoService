package autoservice.domain.service;

import autoservice.domain.model.ServiceOrder;
import autoservice.domain.repository.ServiceOrderRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ServiceOrderService {
    private final ServiceOrderRepository orderRepository;

    public ServiceOrderService(ServiceOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ServiceOrder createOrder(Long carId, String description, String status) {
        ServiceOrder order = new ServiceOrder(null, carId, LocalDate.now(),
                description, null, null, status);
        return orderRepository.save(order);
    }

    public Optional<ServiceOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<ServiceOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<ServiceOrder> getOrdersByCarId(Long carId) {
        return orderRepository.findByCarId(carId);
    }

    public List<ServiceOrder> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public boolean updateOrderStatus(Long id, String status) {
        Optional<ServiceOrder> order = orderRepository.findById(id);
        if (order.isPresent()) {
            ServiceOrder existingOrder = order.get();
            existingOrder.setStatus(status);
            if ("COMPLETED".equals(status)) {
                existingOrder.setCompletionDate(LocalDate.now());
            }
            orderRepository.save(existingOrder);
            return true;
        }
        return false;
    }

    public boolean completeOrder(Long id, String repairDescription) {
        Optional<ServiceOrder> order = orderRepository.findById(id);
        if (order.isPresent()) {
            ServiceOrder existingOrder = order.get();
            existingOrder.setStatus("COMPLETED");
            existingOrder.setRepairDescription(repairDescription);
            existingOrder.setCompletionDate(LocalDate.now());
            orderRepository.save(existingOrder);
            return true;
        }
        return false;
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
