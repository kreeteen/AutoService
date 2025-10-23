package autoservice.cli.command.client;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class CreateClientCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public CreateClientCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- СОЗДАНИЕ КЛИЕНТА ---");
        System.out.print("Имя: ");
        String firstName = scanner.nextLine();
        System.out.print("Фамилия: ");
        String lastName = scanner.nextLine();
        System.out.print("Отчество: ");
        String middleName = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();

        var client = autoService.createClient(firstName, lastName, middleName, phone);
        System.out.println("Клиент создан с ID: " + client.getClientId());
    }

    @Override
    public String getDescription() {
        return "Создать клиента";
    }
}