package autoservice.web.listener;

import autoservice.domain.repository.*;
import autoservice.domain.service.*;
import autoservice.jdbc.DatabaseConnection;
import autoservice.domain.service.*;
import autoservice.domain.repository.*;
import autoservice.jdbc.RepositoryFactory;
import autoservice.jdbc.RepositoryFactory.StorageType;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== ИНИЦИАЛИЗАЦИЯ ВЕБ-ПРИЛОЖЕНИЯ АВТОСЕРВИС ===");

        ServletContext context = sce.getServletContext();

        // Получаем тип хранилища из конфигурации
        String storageTypeParam = context.getInitParameter("storageType");
        StorageType storageType = "jdbc".equalsIgnoreCase(storageTypeParam)
                ? StorageType.JDBC : StorageType.IN_MEMORY;

        System.out.println("Тип хранилища: " + storageType);
        RepositoryFactory.setStorageType(storageType);

        // Инициализируем сервисы
        AutoServiceFacade autoService = initializeServices();

        // Сохраняем фасад в контексте приложения
        context.setAttribute("autoService", autoService);

        System.out.println("Веб-приложение успешно инициализировано");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== ЗАВЕРШЕНИЕ РАБОТЫ ВЕБ-ПРИЛОЖЕНИЯ ===");
        if (RepositoryFactory.getStorageType() == StorageType.JDBC) {
            DatabaseConnection.closeConnection();
        }
    }

    private AutoServiceFacade initializeServices() {
        // Используем фабрику для создания репозиториев
        ClientRepository clientRepository = RepositoryFactory.createClientRepository();
        EmployeeRepository employeeRepository = RepositoryFactory.createEmployeeRepository();
        CarRepository carRepository = RepositoryFactory.createCarRepository();
        ServiceOrderRepository orderRepository = RepositoryFactory.createServiceOrderRepository();
        OrderEmployeeRepository orderEmployeeRepository = RepositoryFactory.createOrderEmployeeRepository();
        PartReplacementRepository partReplacementRepository = RepositoryFactory.createPartReplacementRepository();

        // Создаем сервисы
        ClientService clientService = new ClientService(clientRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);
        CarService carService = new CarService(carRepository);
        ServiceOrderService orderService = new ServiceOrderService(orderRepository);
        OrderEmployeeService orderEmployeeService = new OrderEmployeeService(orderEmployeeRepository);
        PartReplacementService partReplacementService = new PartReplacementService(partReplacementRepository);

        // Создаем фасад
        return new AutoServiceFacade(
                clientService,
                employeeService,
                carService,
                orderService,
                orderEmployeeService,
                partReplacementService
        );
    }
}