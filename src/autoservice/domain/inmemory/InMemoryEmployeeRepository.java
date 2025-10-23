package autoservice.domain.inmemory;

import autoservice.domain.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryEmployeeRepository implements autoservice.domain.repository.EmployeeRepository {
    private final Map<Long, Employee> employees = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Employee save(Employee employee) {
        if (employee.getEmployeeId() == null) {
            employee.setEmployeeId(idCounter.getAndIncrement());
        }
        employees.put(employee.getEmployeeId(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public void deleteById(Long id) {
        employees.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return employees.containsKey(id);
    }

    @Override
    public List<Employee> findByPosition(String position) {
        return employees.values().stream()
                .filter(employee -> position.equals(employee.getPosition()))
                .collect(Collectors.toList());
    }
}
