package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class DeleteEmployeeCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public DeleteEmployeeCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID сотрудника для удаления: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            if (autoService.deleteEmployee(id)) {
                System.out.println("Сотрудник удален");
            } else {
                System.out.println("Сотрудник не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Удалить сотрудника";
    }
}