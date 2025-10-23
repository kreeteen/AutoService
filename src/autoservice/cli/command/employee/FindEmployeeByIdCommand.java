package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindEmployeeByIdCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindEmployeeByIdCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID сотрудника: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            autoService.getEmployee(id).ifPresentOrElse(
                    emp -> {
                        System.out.println("\n--- НАЙДЕН СОТРУДНИК ---");
                        System.out.printf("ID: %d%n", emp.getEmployeeId());
                        System.out.printf("ФИО: %s %s %s%n", emp.getLastName(), emp.getFirstName(), emp.getMiddleName());
                        System.out.printf("Должность: %s%n", emp.getPosition());
                        System.out.printf("Зарплата: %.2f%n", emp.getSalary());
                        System.out.printf("Опыт: %d лет%n", emp.getExperience());
                    },
                    () -> System.out.println("Сотрудник не найден")
            );
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти сотрудника по ID";
    }
}