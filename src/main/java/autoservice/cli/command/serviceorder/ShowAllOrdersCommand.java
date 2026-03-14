package autoservice.cli.command.serviceorder;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.time.format.DateTimeFormatter;

public class ShowAllOrdersCommand implements Command {
    private final AutoServiceFacade autoService;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ShowAllOrdersCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var orders = autoService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказы не найдены");
        } else {
            System.out.println("\n--- СПИСОК ЗАКАЗОВ ---");
            orders.forEach(order ->
                    System.out.printf("ID: %d, Авто ID: %d, Статус: %s, Создан: %s%n",
                            order.getOrderId(), order.getCarId(), order.getStatus(),
                            order.getCreatedDate().format(DATE_FORMATTER)));
        }
    }

    @Override
    public String getDescription() {
        return "Показать все заказы";
    }
}