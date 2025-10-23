package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class CreateOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public CreateOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- СОЗДАНИЕ ЗАКАЗА ---");
        try {
            System.out.print("ID автомобиля: ");
            Long carId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Описание работ: ");
            String description = scanner.nextLine();
            System.out.print("Статус (NEW/IN_PROGRESS/COMPLETED/CANCELLED): ");
            String status = scanner.nextLine();

            var order = autoService.createOrder(carId, description, status);
            System.out.println("Заказ создан с ID: " + order.getOrderId());
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Создать заказ";
    }
}