package autoservice.web.controller;

import autoservice.domain.model.Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet(name = "EmployeeServlet", urlPatterns = {"/employees", "/employees/*"})
public class EmployeeServlet extends BaseServlet {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Список сотрудников
            List<Employee> employees = getAutoService().getAllEmployees();
            request.setAttribute("employees", employees);
            forwardToJsp("employee/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            // Форма создания нового сотрудника
            forwardToJsp("employee/create.jsp", request, response);

        } else if (pathInfo.startsWith("/edit/")) {
            // Форма редактирования сотрудника
            try {
                Long employeeId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getEmployee(employeeId).ifPresent(employee -> {
                    request.setAttribute("employee", employee);
                });
                forwardToJsp("employee/edit.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID сотрудника");
            }

        } else if (pathInfo.startsWith("/view/")) {
            // Просмотр сотрудника
            try {
                Long employeeId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getEmployee(employeeId).ifPresent(employee -> {
                    request.setAttribute("employee", employee);
                    request.setAttribute("assignments", getAutoService().getEmployeeAssignments(employeeId));
                });
                forwardToJsp("employee/view.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID сотрудника");
            }

        } else if (pathInfo.equals("/byPosition")) {
            // Поиск сотрудников по должности
            String position = request.getParameter("position");
            if (position != null && !position.trim().isEmpty()) {
                List<Employee> employees = getAutoService().getEmployeesByPosition(position);
                request.setAttribute("employees", employees);
                request.setAttribute("searchPosition", position);
            }
            forwardToJsp("employee/byPosition.jsp", request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createEmployee(request, response);
        } else if ("update".equals(action)) {
            updateEmployee(request, response);
        } else if ("updateSalary".equals(action)) {
            updateEmployeeSalary(request, response);
        } else if ("delete".equals(action)) {
            deleteEmployee(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            String firstName = getStringParameter(request, "firstName");
            String lastName = getStringParameter(request, "lastName");
            String middleName = getStringParameter(request, "middleName");
            String address = getStringParameter(request, "address");
            LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"), DATE_FORMATTER);
            String phone = getStringParameter(request, "phone");
            String position = getStringParameter(request, "position");
            Double salary = Double.parseDouble(request.getParameter("salary"));
            Integer experience = Integer.parseInt(request.getParameter("experience"));
            String workSchedule = getStringParameter(request, "workSchedule");
            Double seniorityBonus = Double.parseDouble(request.getParameter("seniorityBonus"));

            Employee employee = getAutoService().createEmployee(
                    firstName, lastName, middleName, address, dateOfBirth, phone,
                    position, salary, experience, workSchedule, seniorityBonus
            );

            setSuccessMessage(request, "Сотрудник успешно создан с ID: " + employee.getEmployeeId());
            redirectTo(response, "/employees");

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка при создании сотрудника: " + e.getMessage());
            redirectTo(response, "/employees/new");
        }
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long employeeId = getLongParameter(request, "employeeId");
            if (employeeId == null) {
                setErrorMessage(request, "Неверный ID сотрудника");
                redirectTo(response, "/employees");
                return;
            }

            String firstName = getStringParameter(request, "firstName");
            String lastName = getStringParameter(request, "lastName");
            String middleName = getStringParameter(request, "middleName");
            String address = getStringParameter(request, "address");
            LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"), DATE_FORMATTER);
            String phone = getStringParameter(request, "phone");
            String position = getStringParameter(request, "position");
            Double salary = Double.parseDouble(request.getParameter("salary"));
            Integer experience = Integer.parseInt(request.getParameter("experience"));
            String workSchedule = getStringParameter(request, "workSchedule");
            Double seniorityBonus = Double.parseDouble(request.getParameter("seniorityBonus"));

            // Сначала удаляем, потом создаем заново (упрощенная реализация)
            getAutoService().deleteEmployee(employeeId);
            Employee employee = getAutoService().createEmployee(
                    firstName, lastName, middleName, address, dateOfBirth, phone,
                    position, salary, experience, workSchedule, seniorityBonus
            );

            setSuccessMessage(request, "Данные сотрудника обновлены. Новый ID: " + employee.getEmployeeId());
            redirectTo(response, "/employees");

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка обновления сотрудника: " + e.getMessage());
            redirectTo(response, "/employees");
        }
    }

    private void updateEmployeeSalary(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long employeeId = getLongParameter(request, "employeeId");
        Double newSalary = null;

        try {
            newSalary = Double.parseDouble(request.getParameter("newSalary"));
        } catch (NumberFormatException e) {
            setErrorMessage(request, "Неверный формат зарплаты");
            redirectTo(response, "/employees");
            return;
        }

        if (employeeId == null || newSalary == null) {
            setErrorMessage(request, "Неверные данные");
            redirectTo(response, "/employees");
            return;
        }

        boolean updated = getAutoService().updateEmployeeSalary(employeeId, newSalary);

        if (updated) {
            setSuccessMessage(request, "Зарплата обновлена");
        } else {
            setErrorMessage(request, "Сотрудник не найден");
        }

        redirectTo(response, "/employees");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long employeeId = getLongParameter(request, "employeeId");
        if (employeeId == null) {
            setErrorMessage(request, "Неверный ID сотрудника");
            redirectTo(response, "/employees");
            return;
        }

        boolean deleted = getAutoService().deleteEmployee(employeeId);

        if (deleted) {
            setSuccessMessage(request, "Сотрудник удален");
        } else {
            setErrorMessage(request, "Сотрудник не найден");
        }

        redirectTo(response, "/employees");
    }
}