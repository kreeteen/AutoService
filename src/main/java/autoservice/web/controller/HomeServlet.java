package autoservice.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import autoservice.domain.model.ServiceOrder;
import autoservice.domain.service.AutoServiceFacade;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"", "/", "/home"})
public class HomeServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            AutoServiceFacade autoService = getAutoService();

            // Получаем статистику
            int clientsCount = autoService.getAllClients().size();
            int employeesCount = autoService.getAllEmployees().size();
            int carsCount = autoService.getAllCars().size();
            int ordersCount = autoService.getAllOrders().size();

            // Получаем последние 5 заказов
            List<ServiceOrder> allOrders = autoService.getAllOrders();
            List<ServiceOrder> recentOrders = allOrders;
            if (allOrders.size() > 5) {
                recentOrders = allOrders.subList(0, Math.min(5, allOrders.size()));
            }

            request.setAttribute("clientsCount", clientsCount);
            request.setAttribute("employeesCount", employeesCount);
            request.setAttribute("carsCount", carsCount);
            request.setAttribute("ordersCount", ordersCount);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("allCars", autoService.getAllCars());

            forwardToJsp("home/index.jsp", request, response);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorMessage(request, "Ошибка загрузки данных: " + e.getMessage());
            forwardToJsp("error/500.jsp", request, response);
        }
    }
}