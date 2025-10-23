package autoservice.domain.repository;

import autoservice.domain.model.ServiceOrder;

import java.util.List;
import java.util.Optional;

public interface ServiceOrderRepository {
    ServiceOrder save(ServiceOrder order);
    Optional<ServiceOrder> findById(Long id);
    List<ServiceOrder> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<ServiceOrder> findByCarId(Long carId);
    List<ServiceOrder> findByStatus(String status);
}
