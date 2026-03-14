package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FindOrdersByStatusCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public FindOrdersByStatusCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите статус для поиска (NEW/IN_PROGRESS/COMPLETED/CANCELLED): ");
        String status = scanner.nextLine();

        var orders = autoService.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            System.out.println("Заказы со статусом '" + status + "' не найдены");
        } else {
            System.out.println("\n--- ЗАКАЗЫ СО СТАТУСОМ '" + status + "' ---");
            orders.forEach(order ->
                    System.out.printf("ID: %d, Авто ID: %d, Создан: %s%n",
                            order.getOrderId(), order.getCarId(), order.getCreatedDate().format(DATE_FORMATTER)));
        }
    }

    @Override
    public String getDescription() {
        return "Найти заказы по статусу";
    }
}