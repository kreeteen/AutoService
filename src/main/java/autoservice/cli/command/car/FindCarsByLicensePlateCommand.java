package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.util.Scanner;

public class FindCarsByLicensePlateCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindCarsByLicensePlateCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите номерной знак для поиска: ");
        String licensePlate = scanner.nextLine();

        var cars = autoService.getCarsByLicensePlate(licensePlate);
        if (cars.isEmpty()) {
            System.out.println("Автомобили с номерным знаком '" + licensePlate + "' не найдены");
        } else {
            System.out.println("\n--- АВТОМОБИЛИ С НОМЕРНЫМ ЗНАКОМ '" + licensePlate + "' ---");
            cars.forEach(car ->
                    System.out.printf("ID: %d, Клиент ID: %d, %s %s, Год выпуска: %d%n",
                            car.getCarId(), car.getClientId(), car.getBrand(), car.getModel(),
                            car.getManufactureDate().getYear()));
        }
    }

    @Override
    public String getDescription() {
        return "Найти автомобили по номерному знаку";
    }
}