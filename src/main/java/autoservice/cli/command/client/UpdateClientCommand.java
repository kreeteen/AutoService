package autoservice.cli.command.client;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class UpdateClientCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public UpdateClientCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID клиента для обновления: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            var clientOpt = autoService.getClient(id);
            if (clientOpt.isPresent()) {
                System.out.print("Новое имя: ");
                String firstName = scanner.nextLine();
                System.out.print("Новая фамилия: ");
                String lastName = scanner.nextLine();
                System.out.print("Новое отчество: ");
                String middleName = scanner.nextLine();
                System.out.print("Новый телефон: ");
                String phone = scanner.nextLine();

                if (autoService.updateClient(id, firstName, lastName, middleName, phone)) {
                    System.out.println("Данные клиента обновлены");
                } else {
                    System.out.println("Ошибка при обновлении");
                }
            } else {
                System.out.println("Клиент не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Обновить данные клиента";
    }
}