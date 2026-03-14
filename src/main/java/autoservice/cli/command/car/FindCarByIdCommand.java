package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FindCarByIdCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public FindCarByIdCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID автомобиля: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            autoService.getCar(id).ifPresentOrElse(
                    car -> {
                        System.out.println("\n--- НАЙДЕН АВТОМОБИЛЬ ---");
                        System.out.printf("ID: %d%n", car.getCarId());
                        System.out.printf("Клиент ID: %d%n", car.getClientId());
                        System.out.printf("Марка: %s%n", car.getBrand());
                        System.out.printf("Модель: %s%n", car.getModel());
                        System.out.printf("Номерной знак: %s%n", car.getLicensePlate());
                        System.out.printf("Дата выпуска: %s%n", car.getManufactureDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                    },
                    () -> System.out.println("Автомобиль не найден")
            );
        } catch (Exception e) {
            System.out.println("Ошибка ввода ID!");
            scanner.nextLine();
        }
    }

    @Override
    public String getDescription() {
        return "Найти автомобиль по ID";
    }
}