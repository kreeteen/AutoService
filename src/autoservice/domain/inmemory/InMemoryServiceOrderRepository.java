package autoservice.domain.inmemory;

import autoservice.domain.model.ServiceOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryServiceOrderRepository implements autoservice.domain.repository.ServiceOrderRepository {
    private final Map<Long, ServiceOrder> orders = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public ServiceOrder save(ServiceOrder order) {
        if (order.getOrderId() == null) {
            order.setOrderId(idCounter.getAndIncrement());
        }
        orders.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Optional<ServiceOrder> findById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<ServiceOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public void deleteById(Long id) {
        orders.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return orders.containsKey(id);
    }

    @Override
    public List<ServiceOrder> findByCarId(Long carId) {
        return orders.values().stream()
                .filter(order -> carId.equals(order.getCarId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceOrder> findByStatus(String status) {
        return orders.values().stream()
                .filter(order -> status.equalsIgnoreCase(order.getStatus()))
                .collect(Collectors.toList());
    }
}
