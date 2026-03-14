package autoservice.cli.menu;

import autoservice.cli.command.Command;
import autoservice.cli.command.client.*;
import autoservice.cli.command.client.*;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientMenu implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public ClientMenu(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new CreateClientCommand(autoService, scanner));
        commands.put(2, new ShowAllClientsCommand(autoService));
        commands.put(3, new FindClientByIdCommand(autoService, scanner));
        commands.put(4, new UpdateClientCommand(autoService, scanner));
        commands.put(5, new DeleteClientCommand(autoService, scanner));
    }

    @Override
    public void execute() {
        while (true) {
            showMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 6) {
                    return;
                }

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Неверный выбор!");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода!");
                scanner.nextLine();
            }
        }
    }

    @Override
    public String getDescription() {
        return "Управление клиентами";
    }

    private void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ КЛИЕНТАМИ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("6. Назад");
        System.out.print("Выберите опцию: ");
    }
}