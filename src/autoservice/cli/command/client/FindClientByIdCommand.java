package autoservice.cli.command.client;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindClientByIdCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindClientByIdCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID клиента: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            autoService.getClient(id).ifPresentOrElse(
                    client -> {
                        System.out.println("\n--- НАЙДЕН КЛИЕНТ ---");
                        System.out.printf("ID: %d%n", client.getClientId());
                        System.out.printf("ФИО: %s %s %s%n", client.getLastName(), client.getFirstName(), client.getMiddleName());
                        System.out.printf("Телефон: %s%n", client.getPhone());
                    },
                    () -> System.out.println("Клиент не найден")
            );
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти клиента по ID";
    }
}