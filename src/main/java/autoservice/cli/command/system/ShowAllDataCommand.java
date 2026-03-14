package autoservice.cli.command.system;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllDataCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllDataCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        System.out.println("\n=== ПОЛНЫЙ ОБЗОР ДАННЫХ ===");

        System.out.println("\n--- КЛИЕНТЫ ---");
        var clients = autoService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("Нет клиентов");
        } else {
            clients.forEach(client ->
                    System.out.printf("ID: %d, %s %s %s, Телефон: %s%n",
                            client.getClientId(), client.getLastName(), client.getFirstName(),
                            client.getMiddleName(), client.getPhone()));
        }

        System.out.println("\n--- СОТРУДНИКИ ---");
        var employees = autoService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Нет сотрудников");
        } else {
            employees.forEach(emp ->
                    System.out.printf("ID: %d, %s %s, %s, Зарплата: %.2f%n",
                            emp.getEmployeeId(), emp.getLastName(), emp.getFirstName(),
                            emp.getPosition(), emp.getSalary()));
        }

        System.out.println("\n--- АВТОМОБИЛИ ---");
        var cars = autoService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("Нет автомобилей");
        } else {
            cars.forEach(car ->
                    System.out.printf("ID: %d, Клиент ID: %d, %s %s, Номер: %s%n",
                            car.getCarId(), car.getClientId(), car.getBrand(), car.getModel(), car.getLicensePlate()));
        }

        System.out.println("\n--- ЗАКАЗЫ ---");
        var orders = autoService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет заказов");
        } else {
            orders.forEach(order ->
                    System.out.printf("ID: %d, Авто ID: %d, Статус: %s, Описание: %s%n",
                            order.getOrderId(), order.getCarId(), order.getStatus(), order.getDescription()));
        }

        System.out.println("\n--- НАЗНАЧЕНИЯ СОТРУДНИКОВ ---");
        boolean hasAssignments = false;
        for (var order : orders) {
            var assignments = autoService.getOrderEmployees(order.getOrderId());
            if (!assignments.isEmpty()) {
                hasAssignments = true;
                System.out.printf("Заказ ID %d:%n", order.getOrderId());
                for (var ass : assignments) {
                    autoService.getEmployee(ass.getEmployeeId()).ifPresent(emp ->
                            System.out.printf("  - Сотрудник ID: %d, Роль: %s%n",
                                    ass.getEmployeeId(), ass.getRole()));
                }
            }
        }
        if (!hasAssignments) {
            System.out.println("Нет назначений");
        }

        System.out.println("\n--- ЗАПЧАСТИ ---");
        var parts = autoService.getAllPartReplacements();
        if (parts.isEmpty()) {
            System.out.println("Нет запчастей");
        } else {
            parts.forEach(part ->
                    System.out.printf("Заказ ID: %d, %s (№%s), Количество: %d%n",
                            part.getOrderId(), part.getPartName(), part.getPartNumber(), part.getQuantity()));
        }
    }

    @Override
    public String getDescription() {
        return "Показать все данные";
    }
}