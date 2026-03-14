package autoservice.cli.menu;

import autoservice.cli.command.Command;
import autoservice.cli.command.serviceorder.*;
import autoservice.cli.command.serviceorder.*;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ServiceOrderMenu implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public ServiceOrderMenu(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new CreateOrderCommand(autoService, scanner));
        commands.put(2, new ShowAllOrdersCommand(autoService));
        commands.put(3, new FindOrderByIdCommand(autoService, scanner));
        commands.put(4, new FindOrdersByCarCommand(autoService, scanner));
        commands.put(5, new FindOrdersByStatusCommand(autoService, scanner));
        commands.put(6, new UpdateOrderStatusCommand(autoService, scanner));
        commands.put(7, new CompleteOrderCommand(autoService, scanner));
        commands.put(8, new DeleteOrderCommand(autoService, scanner));
    }

    @Override
    public void execute() {
        while (true) {
            showMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 9) {
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
        return "Управление заказами";
    }

    private void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ ЗАКАЗАМИ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("9. Назад");
        System.out.print("Выберите опцию: ");
    }
}