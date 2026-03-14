package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindCarsByClientCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindCarsByClientCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID клиента: ");
        try {
            Long clientId = scanner.nextLong();
            scanner.nextLine();

            var cars = autoService.getClientCars(clientId);
            if (cars.isEmpty()) {
                System.out.println("У клиента ID " + clientId + " нет автомобилей");
            } else {
                System.out.println("\n--- АВТОМОБИЛИ КЛИЕНТА ID " + clientId + " ---");
                cars.forEach(car ->
                        System.out.printf("ID: %d, %s %s, Номер: %s, Год выпуска: %d%n",
                                car.getCarId(), car.getBrand(), car.getModel(),
                                car.getLicensePlate(), car.getManufactureDate().getYear()));
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти автомобили клиента";
    }
}