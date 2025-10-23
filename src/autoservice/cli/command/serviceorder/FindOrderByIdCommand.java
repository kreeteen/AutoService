package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FindOrderByIdCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public FindOrderByIdCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            autoService.getOrder(id).ifPresentOrElse(
                    order -> {
                        System.out.println("\n--- НАЙДЕН ЗАКАЗ ---");
                        System.out.printf("ID: %d%n", order.getOrderId());
                        System.out.printf("Авто ID: %d%n", order.getCarId());
                        System.out.printf("Статус: %s%n", order.getStatus());
                        System.out.printf("Описание: %s%n", order.getDescription());
                        System.out.printf("Создан: %s%n", order.getCreatedDate().format(DATE_FORMATTER));
                        if (order.getCompletionDate() != null) {
                            System.out.printf("Завершен: %s%n", order.getCompletionDate().format(DATE_FORMATTER));
                        }
                        if (order.getRepairDescription() != null) {
                            System.out.printf("Описание ремонта: %s%n", order.getRepairDescription());
                        }
                    },
                    () -> System.out.println("Заказ не найден")
            );
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти заказ по ID";
    }
}