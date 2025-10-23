package autoservice.cli.command.orderemployee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class ShowOrdersByEmployeeCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public ShowOrdersByEmployeeCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID сотрудника: ");
        try {
            Long employeeId = scanner.nextLong();
            scanner.nextLine();

            var assignments = autoService.getEmployeeAssignments(employeeId);
            if (assignments.isEmpty()) {
                System.out.println("Сотрудник ID " + employeeId + " не назначен на заказы");
            } else {
                System.out.println("\n--- ЗАКАЗЫ СОТРУДНИКА ID " + employeeId + " ---");
                assignments.forEach(ass -> {
                    autoService.getOrder(ass.getOrderId()).ifPresent(order ->
                            System.out.printf("Заказ ID: %d, Статус: %s, Роль: %s%n",
                                    order.getOrderId(), order.getStatus(), ass.getRoleInOrder()));
                });
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Показать заказы сотрудника";
    }
}