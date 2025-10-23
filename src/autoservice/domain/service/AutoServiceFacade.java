package autoservice.domain.service;

import autoservice.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AutoServiceFacade {
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final CarService carService;
    private final ServiceOrderService orderService;
    private final OrderEmployeeService orderEmployeeService;
    private final PartReplacementService partReplacementService;

    public AutoServiceFacade(ClientService clientService, EmployeeService employeeService,
                             CarService carService, ServiceOrderService orderService,
                             OrderEmployeeService orderEmployeeService, PartReplacementService partReplacementService) {
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.carService = carService;
        this.orderService = orderService;
        this.orderEmployeeService = orderEmployeeService;
        this.partReplacementService = partReplacementService;
    }

    // Client operations
    public Client createClient(String firstName, String lastName, String middleName, String phone) {
        return clientService.createClient(firstName, lastName, middleName, phone);
    }

    public Optional<Client> getClient(Long id) {
        return clientService.getClientById(id);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public boolean updateClient(Long id, String firstName, String lastName, String middleName, String phone) {
        return clientService.updateClient(id, firstName, lastName, middleName, phone);
    }

    public boolean deleteClient(Long id) {
        return clientService.deleteClient(id);
    }

    // Employee operations
    public Employee createEmployee(String firstName, String lastName, String middleName,
                                   String address, LocalDate dateOfBirth, String phoneNumber,
                                   String position, Double salary, Integer experience,
                                   String workSchedule, Double seniorityBonus) {
        return employeeService.createEmployee(firstName, lastName, middleName, address,
                dateOfBirth, phoneNumber, position, salary,
                experience, workSchedule, seniorityBonus);
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeService.getEmployeeById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    public List<Employee> getEmployeesByPosition(String position) {
        return employeeService.getEmployeesByPosition(position);
    }

    public boolean updateEmployeeSalary(Long id, Double newSalary) {
        return employeeService.updateEmployeeSalary(id, newSalary);
    }

    public boolean deleteEmployee(Long id) {
        return employeeService.deleteEmployee(id);
    }

    // Car operations
    public Car createCar(Long clientId, String licensePlate, String brand,
                         String model, LocalDate manufactureDate) {
        try {
            return carService.createCar(clientId, licensePlate, brand, model, manufactureDate);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    public Optional<Car> getCar(Long id) {
        return carService.getCarById(id);
    }

    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    public List<Car> getClientCars(Long clientId) {
        return carService.getCarsByClientId(clientId);
    }

    public List<Car> getCarsByLicensePlate(String licensePlate) { return carService.getCarsByLicensePlate(licensePlate);}

    public boolean updateCarLicensePlate(Long id, String newLicensePlate) {
        try {
            return carService.updateCarLicensePlate(id, newLicensePlate);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            throw e;
        }
    }

    public boolean deleteCar(Long id) {
        return carService.deleteCar(id);
    }

    // Order operations
    public ServiceOrder createOrder(Long carId, String description, String status) {
        return orderService.createOrder(carId, description, status);
    }

    public Optional<ServiceOrder> getOrder(Long id) {
        return orderService.getOrderById(id);
    }

    public List<ServiceOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    public List<ServiceOrder> getCarOrders(Long carId) {
        return orderService.getOrdersByCarId(carId);
    }

    public List<ServiceOrder> getOrdersByStatus(String status) {
        return orderService.getOrdersByStatus(status);
    }

    public boolean updateOrderStatus(Long orderId, String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    public boolean completeOrder(Long orderId, String repairDescription) {
        return orderService.completeOrder(orderId, repairDescription);
    }

    public boolean deleteOrder(Long id) {
        return orderService.deleteOrder(id);
    }

    // OrderEmployee operations
    public OrderEmployee assignEmployeeToOrder(Long employeeId, Long orderId, String role) {
        return orderEmployeeService.assignEmployeeToOrder(employeeId, orderId, role);
    }

    public List<OrderEmployee> getOrderEmployees(Long orderId) {
        return orderEmployeeService.getEmployeesByOrder(orderId);
    }

    public List<OrderEmployee> getEmployeeAssignments(Long employeeId) {
        return orderEmployeeService.getOrdersByEmployee(employeeId);
    }

    // PartReplacement operations
    public PartReplacement addPartReplacement(Long orderId, String partName, String partNumber, Integer quantity) {
        return partReplacementService.addPartReplacement(orderId, partName, partNumber, quantity);
    }

    public List<PartReplacement> getOrderPartReplacements(Long orderId) {
        return partReplacementService.getReplacementsByOrder(orderId);
    }

    public List<PartReplacement> getAllPartReplacements() {
        return partReplacementService.getAllReplacements();
    }
}