package autoservice.cli.command.partreplacement;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class ShowPartsByOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public ShowPartsByOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа: ");
        try {
            Long orderId = scanner.nextLong();
            scanner.nextLine();

            var parts = autoService.getOrderPartReplacements(orderId);
            if (parts.isEmpty()) {
                System.out.println("Для заказа ID " + orderId + " не найдены запчасти");
            } else {
                System.out.println("\n--- ЗАПЧАСТИ ДЛЯ ЗАКАЗА ID " + orderId + " ---");
                parts.forEach(part ->
                        System.out.printf("Запчасть: %s (№%s), Количество: %d%n",
                                part.getPartName(), part.getPartNumber(), part.getQuantity()));
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Показать запчасти заказа";
    }
}