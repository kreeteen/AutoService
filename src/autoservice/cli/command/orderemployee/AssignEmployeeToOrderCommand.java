package autoservice.cli.command.orderemployee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class AssignEmployeeToOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public AssignEmployeeToOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- НАЗНАЧЕНИЕ СОТРУДНИКА НА ЗАКАЗ ---");
        try {
            System.out.print("ID сотрудника: ");
            Long employeeId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("ID заказа: ");
            Long orderId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Роль в заказе: ");
            String role = scanner.nextLine();

            autoService.assignEmployeeToOrder(employeeId, orderId, role);
            System.out.println("Сотрудник назначен на заказ");
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Назначить сотрудника на заказ";
    }
}