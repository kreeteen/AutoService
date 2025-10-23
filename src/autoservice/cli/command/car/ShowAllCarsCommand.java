package autoservice.cli.command.car;

import autoservice.cli.command.Command;
import autoservice.domain.service.AutoServiceFacade;

public class ShowAllCarsCommand implements Command {
    private final AutoServiceFacade autoService;

    public ShowAllCarsCommand(AutoServiceFacade autoService) {
        this.autoService = autoService;
    }

    @Override
    public void execute() {
        var cars = autoService.getAllCars();
        if (cars.isEmpty()) {
            System.out.println("Автомобили не найдены");
        } else {
            System.out.println("\n--- СПИСОК АВТОМОБИЛЕЙ ---");
            cars.forEach(car ->
                    System.out.printf("ID: %d, Клиент ID: %d, %s %s, Номер: %s, Год выпуска: %d%n",
                            car.getCarId(), car.getClientId(), car.getBrand(), car.getModel(),
                            car.getLicensePlate(), car.getManufactureDate().getYear()));
        }
    }

    @Override
    public String getDescription() {
        return "Показать все автомобили";
    }
}