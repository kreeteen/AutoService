package autoservice.web.controller;

import autoservice.domain.model.OrderEmployee;
import autoservice.domain.model.Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@WebServlet(name = "AssignmentServlet", urlPatterns = {"/assignments", "/assignments/*"})
public class AssignmentServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<OrderEmployee> assignments = getAutoService().getOrderEmployeesForAll();
            request.setAttribute("assignments", assignments);

            Map<Long, Employee> employeeMap = getEmployeeMap();
            request.setAttribute("employeeMap", employeeMap);

            forwardToJsp("assignment/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            request.setAttribute("employees", getAutoService().getAllEmployees());
            request.setAttribute("orders", getAutoService().getAllOrders());
            forwardToJsp("assignment/create.jsp", request, response);

        } else if (pathInfo.equals("/byOrder")) {
            Long orderId = getLongParameter(request, "orderId");
            if (orderId != null) {
                List<OrderEmployee> assignments = getAutoService().getOrderEmployees(orderId);
                request.setAttribute("assignments", assignments);
                request.setAttribute("searchOrderId", orderId);

                Map<Long, Employee> employeeMap = getEmployeeMap();
                request.setAttribute("employeeMap", employeeMap);
            }
            forwardToJsp("assignment/byOrder.jsp", request, response);

        } else if (pathInfo.equals("/byEmployee")) {
            Long employeeId = getLongParameter(request, "employeeId");
            if (employeeId != null) {
                List<OrderEmployee> assignments = getAutoService().getEmployeeAssignments(employeeId);
                request.setAttribute("assignments", assignments);
                request.setAttribute("searchEmployeeId", employeeId);

                Map<Long, Employee> employeeMap = getEmployeeMap();
                request.setAttribute("employeeMap", employeeMap);
            }
            forwardToJsp("assignment/byEmployee.jsp", request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createAssignment(request, response);
        } else if ("delete".equals(action)) {
            deleteAssignment(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createAssignment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long employeeId = getLongParameter(request, "employeeId");
            Long orderId = getLongParameter(request, "orderId");
            String role = getStringParameter(request, "role");

            if (employeeId == null || orderId == null || role == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/assignments/new");
                return;
            }

            OrderEmployee assignment = getAutoService().assignEmployeeToOrder(employeeId, orderId, role);
            if (assignment != null) {
                setSuccessMessage(request, "Сотрудник назначен на заказ");
                redirectTo(response, "/assignments");
            } else {
                setErrorMessage(request, "Не удалось назначить сотрудника на заказ");
                redirectTo(response, "/assignments/new");
            }

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка назначения: " + e.getMessage());
            redirectTo(response, "/assignments/new");
        }
    }

    private void deleteAssignment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long employeeId = getLongParameter(request, "employeeId");
        Long orderId = getLongParameter(request, "orderId");

        if (employeeId == null || orderId == null) {
            setErrorMessage(request, "Неверные данные");
            redirectTo(response, "/assignments");
            return;
        }

        setErrorMessage(request, "Функция удаления назначения будет реализована в следующей версии");
        redirectTo(response, "/assignments");
    }


    private Map<Long, Employee> getEmployeeMap() {
        try {
            List<Employee> allEmployees = getAutoService().getAllEmployees();
            if (allEmployees != null && !allEmployees.isEmpty()) {
                return allEmployees.stream()
                        .collect(Collectors.toMap(
                                Employee::getEmployeeId,
                                employee -> employee,
                                (existing, replacement) -> existing
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}