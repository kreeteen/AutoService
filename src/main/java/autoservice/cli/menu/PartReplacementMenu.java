package autoservice.cli.menu;

import autoservice.cli.command.Command;
import autoservice.cli.command.partreplacement.AddPartReplacementCommand;
import autoservice.cli.command.partreplacement.ShowAllPartsCommand;
import autoservice.cli.command.partreplacement.ShowPartsByOrderCommand;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PartReplacementMenu implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public PartReplacementMenu(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new AddPartReplacementCommand(autoService, scanner));
        commands.put(2, new ShowPartsByOrderCommand(autoService, scanner));
        commands.put(3, new ShowAllPartsCommand(autoService));
    }

    @Override
    public void execute() {
        while (true) {
            showMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 4) {
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
        return "Управление запчастями";
    }

    private void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ ЗАПЧАСТЯМИ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("4. Назад");
        System.out.print("Выберите опцию: ");
    }
}