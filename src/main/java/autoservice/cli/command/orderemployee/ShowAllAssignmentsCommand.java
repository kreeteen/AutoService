package autoservice.cli.command.orderemployee;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllAssignmentsCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllAssignmentsCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var orders = autoService.getAllOrders();
        boolean hasAssignments = false;

        System.out.println("\n--- ВСЕ НАЗНАЧЕНИЯ СОТРУДНИКОВ ---");
        for (var order : orders) {
            var assignments = autoService.getOrderEmployees(order.getOrderId());
            if (!assignments.isEmpty()) {
                hasAssignments = true;
                System.out.printf("\nЗаказ ID %d (%s):%n", order.getOrderId(), order.getDescription());
                for (var ass : assignments) {
                    autoService.getEmployee(ass.getEmployeeId()).ifPresent(emp ->
                            System.out.printf("  - %s %s (%s)%n",
                                    emp.getLastName(), emp.getFirstName(), ass.getRole()));
                }
            }
        }

        if (!hasAssignments) {
            System.out.println("Нет назначений сотрудников");
        }
    }

    @Override
    public String getDescription() {
        return "Показать все назначения";
    }
}