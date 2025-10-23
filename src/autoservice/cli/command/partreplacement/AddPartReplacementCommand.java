package autoservice.cli.command.partreplacement;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class AddPartReplacementCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public AddPartReplacementCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- ДОБАВЛЕНИЕ ЗАПЧАСТИ ---");
        try {
            System.out.print("ID заказа: ");
            Long orderId = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Название запчасти: ");
            String partName = scanner.nextLine();
            System.out.print("Номер запчасти: ");
            String partNumber = scanner.nextLine();
            System.out.print("Количество: ");
            Integer quantity = scanner.nextInt();
            scanner.nextLine();

            autoService.addPartReplacement(orderId, partName, partNumber, quantity);
            System.out.println("Запчасть добавлена к заказу");
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Добавить запчасть к заказу";
    }
}