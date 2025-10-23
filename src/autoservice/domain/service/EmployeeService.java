package autoservice.domain.service;

import autoservice.domain.model.Employee;
import autoservice.domain.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(String firstName, String lastName, String middleName,
                                   String address, LocalDate dateOfBirth, String phoneNumber,
                                   String position, Double salary, Integer experience,
                                   String workSchedule, Double seniorityBonus) {
        Employee employee = new Employee(null, firstName, lastName, middleName, address,
                dateOfBirth, phoneNumber, position, salary,
                experience, workSchedule, seniorityBonus);
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    public boolean updateEmployeeSalary(Long id, Double newSalary) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            Employee emp = employee.get();
            emp.setSalary(newSalary);
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    public boolean deleteEmployee(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
