package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class CompleteOrderCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public CompleteOrderCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID заказа для завершения: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Введите описание выполненного ремонта: ");
            String repairDescription = scanner.nextLine();

            if (autoService.completeOrder(id, repairDescription)) {
                System.out.println("Заказ завершен");
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
        return "Завершить заказ";
    }
}