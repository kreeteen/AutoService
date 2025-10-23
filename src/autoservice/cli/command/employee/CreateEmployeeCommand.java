package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CreateEmployeeCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CreateEmployeeCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- СОЗДАНИЕ СОТРУДНИКА ---");
        try {
            System.out.print("Имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Фамилия: ");
            String lastName = scanner.nextLine();
            System.out.print("Отчество: ");
            String middleName = scanner.nextLine();
            System.out.print("Адрес: ");
            String address = scanner.nextLine();
            System.out.print("Дата рождения (дд.мм.гггг): ");
            LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            System.out.print("Телефон: ");
            String phone = scanner.nextLine();
            System.out.print("Должность: ");
            String position = scanner.nextLine();
            System.out.print("Зарплата: ");
            Double salary = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Опыт работы (лет): ");
            Integer experience = scanner.nextInt();
            scanner.nextLine();
            System.out.print("График работы: ");
            String workSchedule = scanner.nextLine();
            System.out.print("Надбавка за стаж: ");
            Double seniorityBonus = scanner.nextDouble();
            scanner.nextLine();

            var employee = autoService.createEmployee(firstName, lastName, middleName, address,
                    dateOfBirth, phone, position, salary, experience, workSchedule, seniorityBonus);
            System.out.println("Сотрудник создан с ID: " + employee.getEmployeeId());
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты! Используйте дд.мм.гггг");
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Создать сотрудника";
    }
}