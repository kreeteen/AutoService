package autoservice;

import autoservice.cli.CliRunner;
import autoservice.domain.repository.*;
import autoservice.domain.service.*;
import autoservice.domain.inmemory.*;
import autoservice.jdbc.DatabaseConnection;
import autoservice.jdbc.RepositoryFactory;
import autoservice.jdbc.RepositoryFactory.StorageType;

import java.time.LocalDate;

public class Main {
//    public static void main(String[] args) {
//        AutoServiceFacade autoService = initializeServices();
//        CliRunner cliRunner = new CliRunner(autoService);
//        cliRunner.run();
//    }
    public static void main(String[] args) {
        StorageType storageType = determineStorageType(args);
        RepositoryFactory.setStorageType(storageType);

        AutoServiceFacade autoService = initializeServices();
        CliRunner cliRunner = new CliRunner(autoService);
        cliRunner.run();

        if (storageType == StorageType.JDBC) {
            DatabaseConnection.closeConnection();
        }
    }

    private static StorageType determineStorageType(String[] args) {
        if (args.length > 0 && "jdbc".equalsIgnoreCase(args[0])) {
            System.out.println("Используется JDBC хранилище (PostgreSQL)");
            return StorageType.JDBC;
        } else {
            System.out.println("Используется In-Memory хранилище");
            return StorageType.IN_MEMORY;
        }
    }

    private static AutoServiceFacade initializeServices() {
        ClientRepository clientRepository = RepositoryFactory.createClientRepository();
        EmployeeRepository employeeRepository = RepositoryFactory.createEmployeeRepository();
        CarRepository carRepository = RepositoryFactory.createCarRepository();
        ServiceOrderRepository serviceOrderRepository = RepositoryFactory.createServiceOrderRepository();
        OrderEmployeeRepository orderEmployeeRepository = RepositoryFactory.createOrderEmployeeRepository();
        PartReplacementRepository partReplacementRepository = RepositoryFactory.createPartReplacementRepository();

        ClientService clientService = new ClientService(clientRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        CarService carService = new CarService(carRepository);
        ServiceOrderService orderService = new ServiceOrderService(serviceOrderRepository);
        OrderEmployeeService orderEmployeeService = new OrderEmployeeService(orderEmployeeRepository);
        PartReplacementService partReplacementService = new PartReplacementService(partReplacementRepository);

        AutoServiceFacade autoService = new AutoServiceFacade(
                clientService,
                employeeService,
                carService,
                orderService,
                orderEmployeeService,
                partReplacementService
        );

        // Initialize data only for in-memory
        if (RepositoryFactory.createClientRepository() instanceof InMemoryClientRepository) {
            initializeSampleData(autoService);
        }

        return autoService;
    }

//    private static AutoServiceFacade initializeServices() {
//        ClientRepository clientRepository = new InMemoryClientRepository();
//        EmployeeRepository employeeRepository = new InMemoryEmployeeRepository();
//        CarRepository carRepository = new InMemoryCarRepository();
//        ServiceOrderRepository orderRepository = new InMemoryServiceOrderRepository();
//        OrderEmployeeRepository orderEmployeeRepository = new InMemoryOrderEmployeeRepository();
//        PartReplacementRepository partReplacementRepository = new InMemoryPartReplacementRepository();
//
//        ClientService clientService = new ClientService(clientRepository);
//        EmployeeService employeeService = new EmployeeService(employeeRepository);
//        CarService carService = new CarService(carRepository);
//        ServiceOrderService orderService = new ServiceOrderService(orderRepository);
//        OrderEmployeeService orderEmployeeService = new OrderEmployeeService(orderEmployeeRepository);
//        PartReplacementService partReplacementService = new PartReplacementService(partReplacementRepository);
//
//        AutoServiceFacade autoService = new AutoServiceFacade(
//                clientService,
//                employeeService,
//                carService,
//                orderService,
//                orderEmployeeService,
//                partReplacementService
//        );
//
//        initializeSampleData(autoService);
//        return autoService;
//    }

    private static void initializeSampleData(AutoServiceFacade autoService) {
        Long clientId1 = autoService.createClient("Иван", "Иванов", "Иванович", "+79161234567").getClientId();
        Long clientId2 = autoService.createClient("Петр", "Петров", "Петрович", "+79167654321").getClientId();

        Long employeeId1 = autoService.createEmployee("Сергей", "Сидоров", "Сергеевич",
                "ул. Ленина, 123", LocalDate.of(1985, 5, 15),
                "+79161112233", "Механик", Double.valueOf(50000.0), Integer.valueOf(5),
                "9:00-18:00", Double.valueOf(5000.0)).getEmployeeId();

        Long employeeId2 = autoService.createEmployee("Анна", "Кузнецова", "Владимировна",
                "ул. Мира, 45", LocalDate.of(1990, 8, 20),
                "+79162223344", "Менеджер", Double.valueOf(45000.0), Integer.valueOf(3),
                "8:00-17:00", Double.valueOf(3000.0)).getEmployeeId();

        Long carId1 = autoService.createCar(clientId1, "A123BC777", "Toyota", "Camry", LocalDate.of(2019, 12, 1)).getCarId();
        Long carId2 = autoService.createCar(clientId2, "B456MH777", "BMW", "X5", LocalDate.of(2018, 11, 1)).getCarId();

        Long orderId1 = autoService.createOrder(carId1, "Замена масла и фильтров", "IN_PROGRESS").getOrderId();
        Long orderId2 = autoService.createOrder(carId2, "Ремонт тормозной системы", "PENDING").getOrderId();

        autoService.assignEmployeeToOrder(employeeId1, orderId1, "МЕХАНИК");
        autoService.assignEmployeeToOrder(employeeId2, orderId1, "МЕНЕДЖЕР");
        autoService.assignEmployeeToOrder(employeeId1, orderId2, "МЕХАНИК");

        autoService.addPartReplacement(orderId1, "Моторное масло", "OIL-001", 1);
        autoService.addPartReplacement(orderId1, "Масляный фильтр", "FILTER-002", 1);
        autoService.addPartReplacement(orderId2, "Тормозные колодки", "BRAKE-101", 4);

        autoService.completeOrder(orderId1, "Масло и фильтры успешно заменены");

        System.out.println("=== ТЕСТОВЫЕ ДАННЫЕ СОЗДАНЫ ===");
    }
}