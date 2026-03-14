package autoservice.web.controller;

import autoservice.domain.model.Car;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "CarServlet", urlPatterns = {"/cars", "/cars/*"})
public class CarServlet extends BaseServlet {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Список автомобилей
            List<Car> cars = getAutoService().getAllCars();
            request.setAttribute("cars", cars);
            forwardToJsp("car/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            // Форма создания нового автомобиля
            request.setAttribute("clients", getAutoService().getAllClients());
            forwardToJsp("car/create.jsp", request, response);

        } else if (pathInfo.startsWith("/edit/")) {
            // Форма редактирования автомобиля
            try {
                Long carId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getCar(carId).ifPresent(car -> {
                    request.setAttribute("car", car);
                    request.setAttribute("clients", getAutoService().getAllClients());
                });
                forwardToJsp("car/edit.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID автомобиля");
            }

        } else if (pathInfo.startsWith("/view/")) {
            // Просмотр автомобиля
            try {
                Long carId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getCar(carId).ifPresent(car -> {
                    request.setAttribute("car", car);
                    // Получаем заказы для этого автомобиля
                    request.setAttribute("orders", getAutoService().getCarOrders(carId));
                });
                forwardToJsp("car/view.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID автомобиля");
            }

        } else if (pathInfo.equals("/byLicensePlate")) {
            // Поиск по госномеру
            String licensePlate = request.getParameter("licensePlate");
            if (licensePlate != null && !licensePlate.trim().isEmpty()) {
                List<Car> cars = getAutoService().getCarsByLicensePlate(licensePlate);
                request.setAttribute("cars", cars);
                request.setAttribute("searchLicensePlate", licensePlate);
            }
            forwardToJsp("car/byLicensePlate.jsp", request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createCar(request, response);
        } else if ("update".equals(action)) {
            updateCar(request, response);
        } else if ("updateLicensePlate".equals(action)) {
            updateCarLicensePlate(request, response);
        } else if ("delete".equals(action)) {
            deleteCar(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long clientId = getLongParameter(request, "clientId");
            String licensePlate = getStringParameter(request, "licensePlate");
            String brand = getStringParameter(request, "brand");
            String model = getStringParameter(request, "model");
            LocalDate manufactureDate = LocalDate.parse(request.getParameter("manufactureDate"), DATE_FORMATTER);

            if (clientId == null || licensePlate == null || brand == null || model == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/cars/new");
                return;
            }

            Car car = getAutoService().createCar(clientId, licensePlate, brand, model, manufactureDate);
            setSuccessMessage(request, "Автомобиль создан с ID: " + car.getCarId());
            redirectTo(response, "/cars");

        } catch (IllegalArgumentException e) {
            setErrorMessage(request, "Ошибка: " + e.getMessage());
            redirectTo(response, "/cars/new");
        } catch (Exception e) {
            setErrorMessage(request, "Ошибка создания автомобиля: " + e.getMessage());
            redirectTo(response, "/cars/new");
        }
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long carId = getLongParameter(request, "carId");
            if (carId == null) {
                setErrorMessage(request, "Неверный ID автомобиля");
                redirectTo(response, "/cars");
                return;
            }

            Long clientId = getLongParameter(request, "clientId");
            String licensePlate = getStringParameter(request, "licensePlate");
            String brand = getStringParameter(request, "brand");
            String model = getStringParameter(request, "model");
            LocalDate manufactureDate = LocalDate.parse(request.getParameter("manufactureDate"), DATE_FORMATTER);

            if (clientId == null || licensePlate == null || brand == null || model == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/cars/edit/" + carId);
                return;
            }

            // Получаем существующий автомобиль
            Car existingCar = getAutoService().getCar(carId).orElse(null);
            if (existingCar == null) {
                setErrorMessage(request, "Автомобиль не найден");
                redirectTo(response, "/cars");
                return;
            }

            // Если изменился госномер, обновляем его через специальный метод
            if (!existingCar.getLicensePlate().equals(licensePlate)) {
                getAutoService().updateCarLicensePlate(carId, licensePlate);
            }

            // Создаем новый автомобиль с обновленными данными (упрощенная реализация)
            // В реальном приложении нужен метод updateCar в фасаде
            getAutoService().deleteCar(carId);
            getAutoService().createCar(clientId, licensePlate, brand, model, manufactureDate);

            setSuccessMessage(request, "Автомобиль обновлен");
            redirectTo(response, "/cars");

        } catch (IllegalArgumentException e) {
            setErrorMessage(request, "Ошибка валидации: " + e.getMessage());
            redirectTo(response, "/cars");
        } catch (Exception e) {
            setErrorMessage(request, "Ошибка обновления автомобиля: " + e.getMessage());
            redirectTo(response, "/cars");
        }
    }

    private void updateCarLicensePlate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long carId = getLongParameter(request, "carId");
        String newLicensePlate = getStringParameter(request, "newLicensePlate");

        if (carId == null || newLicensePlate == null) {
            setErrorMessage(request, "Неверные данные");
            redirectTo(response, "/cars");
            return;
        }

        try {
            boolean updated = getAutoService().updateCarLicensePlate(carId, newLicensePlate);

            if (updated) {
                setSuccessMessage(request, "Номерной знак обновлен");
            } else {
                setErrorMessage(request, "Автомобиль не найден");
            }
        } catch (IllegalArgumentException e) {
            setErrorMessage(request, "Ошибка: " + e.getMessage());
        }

        redirectTo(response, "/cars");
    }

    private void deleteCar(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long carId = getLongParameter(request, "carId");
        if (carId == null) {
            setErrorMessage(request, "Неверный ID автомобиля");
            redirectTo(response, "/cars");
            return;
        }

        boolean deleted = getAutoService().deleteCar(carId);

        if (deleted) {
            setSuccessMessage(request, "Автомобиль удален");
        } else {
            setErrorMessage(request, "Автомобиль не найден");
        }

        redirectTo(response, "/cars");
    }
}