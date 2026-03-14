package autoservice.cli.command.partreplacement;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllPartsCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllPartsCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var parts = autoService.getAllPartReplacements();
        if (parts.isEmpty()) {
            System.out.println("Запчасти не найдены");
        } else {
            System.out.println("\n--- ВСЕ ЗАПЧАСТИ ---");
            parts.forEach(part ->
                    System.out.printf("Заказ ID: %d, Запчасть: %s (№%s), Количество: %d%n",
                            part.getOrderId(), part.getPartName(), part.getPartNumber(), part.getQuantity()));
        }
    }

    @Override
    public String getDescription() {
        return "Показать все запчасти";
    }
}