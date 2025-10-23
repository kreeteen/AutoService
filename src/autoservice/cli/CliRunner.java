package autoservice.cli;

import autoservice.cli.command.Command;
import autoservice.cli.command.system.ShowAllDataCommand;
import autoservice.cli.menu.*;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CliRunner {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public CliRunner(AutoServiceFacade autoService) {
        this.autoService = autoService;
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new ClientMenu(autoService, scanner));
        commands.put(2, new EmployeeMenu(autoService, scanner));
        commands.put(3, new CarMenu(autoService, scanner));
        commands.put(4, new ServiceOrderMenu(autoService, scanner));
        commands.put(5, new OrderEmployeeMenu(autoService, scanner));
        commands.put(6, new PartReplacementMenu(autoService, scanner));
        commands.put(7, new ShowAllDataCommand(autoService));
    }

    public void run() {
        while (true) {
            showMainMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 8) {
                    System.out.println("До свидания!");
                    break;
                }

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Неверный выбор! Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода! Введите число от 1 до 8.");
                scanner.nextLine();
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n=== АВТОСЕРВИС - ГЛАВНОЕ МЕНЮ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("8. Выход");
        System.out.print("Выберите опцию: ");
    }
}