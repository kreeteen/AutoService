package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class DeleteCarCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public DeleteCarCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID автомобиля для удаления: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            if (autoService.deleteCar(id)) {
                System.out.println("Автомобиль удален");
            } else {
                System.out.println("Автомобиль не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Удалить автомобиль";
    }
}