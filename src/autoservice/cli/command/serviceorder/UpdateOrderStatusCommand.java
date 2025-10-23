package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class UpdateOrderStatusCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public UpdateOrderStatusCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Введите новый статус (NEW/IN_PROGRESS/COMPLETED/CANCELLED): ");
            String status = scanner.nextLine();

            if (autoService.updateOrderStatus(id, status)) {
                System.out.println("Статус заказа обновлен");
            } else {
                System.out.println("Заказ не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Обновить статус заказа";
    }
}