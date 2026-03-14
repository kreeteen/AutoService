package autoservice.web.controller;

import autoservice.domain.model.ServiceOrder;
import autoservice.domain.model.Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "OrderServlet", urlPatterns = {"/orders", "/orders/*"})
public class OrderServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<ServiceOrder> orders = getAutoService().getAllOrders();
            request.setAttribute("orders", orders);
            forwardToJsp("order/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            request.setAttribute("cars", getAutoService().getAllCars());
            forwardToJsp("order/create.jsp", request, response);

        } else if (pathInfo.startsWith("/edit/")) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getOrder(orderId).ifPresent(order -> {
                    request.setAttribute("order", order);
                    request.setAttribute("cars", getAutoService().getAllCars());
                });
                forwardToJsp("order/edit.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID заказа");
            }

        } else if (pathInfo.startsWith("/view/")) {
            try {
                Long orderId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getOrder(orderId).ifPresent(order -> {
                    request.setAttribute("order", order);
                    request.setAttribute("assignments", getAutoService().getOrderEmployees(orderId));
                    request.setAttribute("parts", getAutoService().getOrderPartReplacements(orderId));

                    Map<Long, Employee> employeeMap = getAutoService().getAllEmployees().stream()
                            .collect(Collectors.toMap(Employee::getEmployeeId, e -> e));
                    request.setAttribute("employeeMap", employeeMap);
                });
                forwardToJsp("order/view.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID заказа");
            }

        } else if (pathInfo.equals("/byStatus")) {
            String status = request.getParameter("status");
            if (status != null && !status.trim().isEmpty()) {
                List<ServiceOrder> orders = getAutoService().getOrdersByStatus(status);
                request.setAttribute("orders", orders);
                request.setAttribute("searchStatus", status);
            }
            forwardToJsp("order/byStatus.jsp", request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createOrder(request, response);
        } else if ("update".equals(action)) {
            updateOrder(request, response);
        } else if ("updateStatus".equals(action)) {
            updateOrderStatus(request, response);
        } else if ("complete".equals(action)) {
            completeOrder(request, response);
        } else if ("delete".equals(action)) {
            deleteOrder(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long carId = getLongParameter(request, "carId");
            String description = getStringParameter(request, "description");
            String status = getStringParameter(request, "status");

            if (carId == null || description == null || status == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/orders/new");
                return;
            }

            ServiceOrder order = getAutoService().createOrder(carId, description, status);
            setSuccessMessage(request, "Заказ создан с ID: " + order.getOrderId());
            redirectTo(response, "/orders");

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка создания заказа: " + e.getMessage());
            redirectTo(response, "/orders/new");
        }
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long orderId = getLongParameter(request, "orderId");
            Long carId = getLongParameter(request, "carId");
            String description = getStringParameter(request, "description");
            String status = getStringParameter(request, "status");

            if (orderId == null || carId == null || description == null || status == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/orders");
                return;
            }

            getAutoService().deleteOrder(orderId);
            getAutoService().createOrder(carId, description, status);

            setSuccessMessage(request, "Заказ обновлен");
            redirectTo(response, "/orders");

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка обновления заказа: " + e.getMessage());
            redirectTo(response, "/orders");
        }
    }

    private void updateOrderStatus(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long orderId = getLongParameter(request, "orderId");
        String status = getStringParameter(request, "status");

        if (orderId == null || status == null) {
            setErrorMessage(request, "Неверные данные");
            redirectTo(response, "/orders");
            return;
        }

        boolean updated = getAutoService().updateOrderStatus(orderId, status);

        if (updated) {
            setSuccessMessage(request, "Статус заказа обновлен");
        } else {
            setErrorMessage(request, "Заказ не найден");
        }

        redirectTo(response, "/orders");
    }

    private void completeOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long orderId = getLongParameter(request, "orderId");
        String repairDescription = getStringParameter(request, "repairDescription");

        if (orderId == null) {
            setErrorMessage(request, "Неверный ID заказа");
            redirectTo(response, "/orders");
            return;
        }

        boolean completed = getAutoService().completeOrder(orderId, repairDescription != null ? repairDescription : "");

        if (completed) {
            setSuccessMessage(request, "Заказ завершен");
        } else {
            setErrorMessage(request, "Заказ не найден");
        }

        redirectTo(response, "/orders");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long orderId = getLongParameter(request, "orderId");
        if (orderId == null) {
            setErrorMessage(request, "Неверный ID заказа");
            redirectTo(response, "/orders");
            return;
        }

        boolean deleted = getAutoService().deleteOrder(orderId);

        if (deleted) {
            setSuccessMessage(request, "Заказ удален");
        } else {
            setErrorMessage(request, "Заказ не найден");
        }

        redirectTo(response, "/orders");
    }
}