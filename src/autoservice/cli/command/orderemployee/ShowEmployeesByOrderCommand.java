package autoservice.cli.command.orderemployee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class ShowEmployeesByOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public ShowEmployeesByOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа: ");
        try {
            Long orderId = scanner.nextLong();
            scanner.nextLine();

            var assignments = autoService.getOrderEmployees(orderId);
            if (assignments.isEmpty()) {
                System.out.println("На заказ ID " + orderId + " не назначены сотрудники");
            } else {
                System.out.println("\n--- СОТРУДНИКИ НА ЗАКАЗЕ ID " + orderId + " ---");
                assignments.forEach(ass -> {
                    autoService.getEmployee(ass.getEmployeeId()).ifPresent(emp ->
                            System.out.printf("Сотрудник: %s %s, Роль: %s%n",
                                    emp.getLastName(), emp.getFirstName(), ass.getRoleInOrder()));
                });
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Показать сотрудников заказа";
    }
}