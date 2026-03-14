package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindEmployeesByPositionCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindEmployeesByPositionCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите должность для поиска: ");
        String position = scanner.nextLine();

        var employees = autoService.getEmployeesByPosition(position);
        if (employees.isEmpty()) {
            System.out.println("Сотрудники с должностью '" + position + "' не найдены");
        } else {
            System.out.println("\n--- СОТРУДНИКИ НА ДОЛЖНОСТИ '" + position + "' ---");
            employees.forEach(emp ->
                    System.out.printf("ID: %d, %s %s, Зарплата: %.2f, Опыт: %d лет%n",
                            emp.getEmployeeId(), emp.getLastName(), emp.getFirstName(),
                            emp.getSalary(), emp.getExperience()));
        }
    }

    @Override
    public String getDescription() {
        return "Найти сотрудников по должности";
    }
}