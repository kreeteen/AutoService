package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class UpdateEmployeeSalaryCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public UpdateEmployeeSalaryCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID сотрудника: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Введите новую зарплату: ");
            Double newSalary = scanner.nextDouble();
            scanner.nextLine();

            if (autoService.updateEmployeeSalary(id, newSalary)) {
                System.out.println("Зарплата обновлена");
            } else {
                System.out.println("Сотрудник не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Обновить зарплату сотрудника";
    }
}