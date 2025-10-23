package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class DeleteOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public DeleteOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа для удаления: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            if (autoService.deleteOrder(id)) {
                System.out.println("Заказ удален");
            } else {
                System.out.println("Заказ не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Удалить заказ";
    }
}