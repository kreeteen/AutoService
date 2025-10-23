package autoservice.cli.menu;

import autoservice.cli.command.Command;
import autoservice.cli.command.orderemployee.AssignEmployeeToOrderCommand;
import autoservice.cli.command.orderemployee.ShowAllAssignmentsCommand;
import autoservice.cli.command.orderemployee.ShowEmployeesByOrderCommand;
import autoservice.cli.command.orderemployee.ShowOrdersByEmployeeCommand;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class OrderEmployeeMenu implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public OrderEmployeeMenu(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new AssignEmployeeToOrderCommand(autoService, scanner));
        commands.put(2, new ShowEmployeesByOrderCommand(autoService, scanner));
        commands.put(3, new ShowOrdersByEmployeeCommand(autoService, scanner));
        commands.put(4, new ShowAllAssignmentsCommand(autoService));
    }

    @Override
    public void execute() {
        while (true) {
            showMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 5) {
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
        return "Назначения сотрудников на заказы";
    }

    private void showMenu() {
        System.out.println("\n=== НАЗНАЧЕНИЯ СОТРУДНИКОВ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("5. Назад");
        System.out.print("Выберите опцию: ");
    }
}