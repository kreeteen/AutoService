package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;
import autoservice.domain.util.LicensePlateValidator;

import java.util.Scanner;

public class UpdateCarLicensePlateCommand implements Command {
    private final AutoServiceFacade autoService;
    private final Scanner scanner;

    public UpdateCarLicensePlateCommand(AutoServiceFacade autoService, Scanner scanner) {
        this.autoService = autoService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID автомобиля: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();

            String newLicensePlate = getValidLicensePlate();
            if (newLicensePlate == null) {
                return; // Пользователь отменил ввод
            }

            if (autoService.updateCarLicensePlate(id, newLicensePlate)) {
                System.out.println("Номерной знак автомобиля обновлен");
            } else {
                System.out.println("Автомобиль не найден");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
            scanner.nextLine();
        }
    }

    private String getValidLicensePlate() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;

        while (attempts < MAX_ATTEMPTS) {
            System.out.print("Введите новый номерной знак: ");
            String licensePlate = scanner.nextLine().trim();

            if (licensePlate.equalsIgnoreCase("отмена")) {
                System.out.println("Обновление отменено.");
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
                    System.out.println("Превышено максимальное количество попыток. Обновление отменено.");
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    public String getDescription() {
        return "Обновить номерной знак автомобиля";
    }
}