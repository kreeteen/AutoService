package autoservice.cli.command.client;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class DeleteClientCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public DeleteClientCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID клиента для удаления: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            if (autoService.deleteClient(id)) {
                System.out.println("Клиент удален");
            } else {
                System.out.println("Клиент не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Удалить клиента";
    }
}