package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindOrdersByCarCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindOrdersByCarCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID автомобиля: ");
        try {
            Long carId = scanner.nextLong();
            scanner.nextLine();

            var orders = autoService.getCarOrders(carId);
            if (orders.isEmpty()) {
                System.out.println("Заказы для автомобиля ID " + carId + " не найдены");
            } else {
                System.out.println("\n--- ЗАКАЗЫ ДЛЯ АВТОМОБИЛЯ ID " + carId + " ---");
                orders.forEach(order ->
                        System.out.printf("ID: %d, Статус: %s, Описание: %s%n",
                                order.getOrderId(), order.getStatus(), order.getDescription()));
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти заказы по автомобилю";
    }
}