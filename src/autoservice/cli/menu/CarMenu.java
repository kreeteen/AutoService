package autoservice.cli.menu;

import autoservice.cli.command.Command;
import autoservice.cli.command.car.*;
import autoservice.domain.service.AutoServiceFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CarMenu implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    public CarMenu(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
        this.commands = new HashMap<>();
        initializeCommands();
    }

    private void initializeCommands() {
        commands.put(1, new CreateCarCommand(autoService, scanner));
        commands.put(2, new ShowAllCarsCommand(autoService));
        commands.put(3, new FindCarByIdCommand(autoService, scanner));
        commands.put(4, new FindCarsByClientCommand(autoService, scanner));
        commands.put(5, new FindCarsByLicensePlateCommand(autoService, scanner));
        commands.put(6, new UpdateCarLicensePlateCommand(autoService, scanner));
        commands.put(7, new DeleteCarCommand(autoService, scanner));
    }

    @Override
    public void execute() {
        while (true) {
            showMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 8) {
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
        return "Управление автомобилями";
    }

    private void showMenu() {
        System.out.println("\n=== УПРАВЛЕНИЕ АВТОМОБИЛЯМИ ===");
        for (Map.Entry<Integer, Command> entry : commands.entrySet()) {
            System.out.printf("%d. %s%n", entry.getKey(), entry.getValue().getDescription());
        }
        System.out.println("8. Назад");
        System.out.print("Выберите опцию: ");
    }
}