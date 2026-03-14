package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;
import autoservice.domain.util.LicensePlateValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CreateCarCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CreateCarCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- СОЗДАНИЕ АВТОМОБИЛЯ ---");
        try {
            System.out.print("ID клиента-владельца: ");
            Long clientId = scanner.nextLong();
            scanner.nextLine();

            String licensePlate = getValidLicensePlate();
            if (licensePlate == null) {
                return; // Пользователь отменил ввод
            }

            System.out.print("Марка: ");
            String brand = scanner.nextLine();
            System.out.print("Модель: ");
            String model = scanner.nextLine();
            System.out.print("Дата выпуска (дд.мм.гггг): ");
            LocalDate manufactureDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

            var car = autoService.createCar(clientId, licensePlate, brand, model, manufactureDate);
            System.out.println("Автомобиль создан с ID: " + car.getCarId());
        } catch (DateTimeParseException e) {
            System.out.println("Неверный формат даты! Используйте дд.мм.гггг");
        } catch (Exception e) {
            System.out.println("Ошибка ввода данных!");
            scanner.nextLine();
        }
    }

    private String getValidLicensePlate() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Номерной знак: ");
            String licensePlate = scanner.nextLine().trim();

            if (licensePlate.equalsIgnoreCase("отмена")) {
                System.out.println("Создание автомобиля отменено.");
                return null;
            }

            if (LicensePlateValidator.isValidRussianLicensePlate(licensePlate)) {
                return LicensePlateValidator.formatLicensePlate(licensePlate);
            } else {
                attempts++;
                System.out.println("Неверный формат номерного знака!");
                System.out.println(LicensePlateValidator.getValidationRules());

                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("Попробуйте снова или введите 'отмена' для выхода.");
                } else {
                    System.out.println("Превышено максимальное количество попыток. Создание автомобиля отменено.");
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    public String getDescription() {
        return "Создать автомобиль";
    }
}