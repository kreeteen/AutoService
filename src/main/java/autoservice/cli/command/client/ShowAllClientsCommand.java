package autoservice.cli.command.client;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllClientsCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllClientsCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var clients = autoService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("Клиенты не найдены");
        } else {
            System.out.println("\n--- СПИСОК КЛИЕНТОВ ---");
            clients.forEach(client ->
                    System.out.printf("ID: %d, %s %s %s, Телефон: %s%n",
                            client.getClientId(), client.getLastName(), client.getFirstName(),
                            client.getMiddleName(), client.getPhone()));
        }
    }

    @Override
    public String getDescription() {
        return "Показать всех клиентов";
    }
}