package autoservice.domain.repository;

import autoservice.domain.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Employee save(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    List<Employee> findByPosition(String position);
}
