package autoservice.cli.command.employee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllEmployeesCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllEmployeesCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var employees = autoService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Сотрудники не найдены");
        } else {
            System.out.println("\n--- СПИСОК СОТРУДНИКОВ ---");
            employees.forEach(emp ->
                    System.out.printf("ID: %d, %s %s, %s, Зарплата: %.2f руб.%n",
                            emp.getEmployeeId(), emp.getLastName(), emp.getFirstName(),
                            emp.getPosition(), emp.getSalary()));
        }
    }

    @Override
    public String getDescription() {
        return "Показать всех сотрудников";
    }
}