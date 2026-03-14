package autoservice.web.controller;

import autoservice.domain.model.Client;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClientServlet", urlPatterns = {"/clients", "/clients/*"})
public class ClientServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Список клиентов
            List<Client> clients = getAutoService().getAllClients();
            request.setAttribute("clients", clients);
            forwardToJsp("client/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            // Форма создания нового клиента
            forwardToJsp("client/create.jsp", request, response);

        } else if (pathInfo.startsWith("/edit/")) {
            // Форма редактирования клиента
            try {
                Long clientId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getClient(clientId).ifPresent(client -> {
                    request.setAttribute("client", client);
                });
                forwardToJsp("client/edit.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID клиента");
            }

        } else if (pathInfo.startsWith("/view/")) {
            // Просмотр клиента
            try {
                Long clientId = Long.parseLong(pathInfo.substring(6));
                getAutoService().getClient(clientId).ifPresent(client -> {
                    request.setAttribute("client", client);
                    // Получаем автомобили клиента
                    request.setAttribute("cars", getAutoService().getClientCars(clientId));
                });
                forwardToJsp("client/view.jsp", request, response);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID клиента");
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createClient(request, response);
        } else if ("update".equals(action)) {
            updateClient(request, response);
        } else if ("delete".equals(action)) {
            deleteClient(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String firstName = getStringParameter(request, "firstName");
        String lastName = getStringParameter(request, "lastName");
        String middleName = getStringParameter(request, "middleName");
        String phone = getStringParameter(request, "phone");

        if (firstName == null || lastName == null || phone == null) {
            setErrorMessage(request, "Заполните обязательные поля (Имя, Фамилия, Телефон)");
            redirectTo(response, "/clients/new");
            return;
        }

        try {
            Client client = getAutoService().createClient(firstName, lastName, middleName, phone);
            setSuccessMessage(request, "Клиент успешно создан с ID: " + client.getClientId());
            redirectTo(response, "/clients");
        } catch (Exception e) {
            setErrorMessage(request, "Ошибка создания клиента: " + e.getMessage());
            redirectTo(response, "/clients/new");
        }
    }

    private void updateClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long clientId = getLongParameter(request, "clientId");
        if (clientId == null) {
            setErrorMessage(request, "Неверный ID клиента");
            redirectTo(response, "/clients");
            return;
        }

        String firstName = getStringParameter(request, "firstName");
        String lastName = getStringParameter(request, "lastName");
        String middleName = getStringParameter(request, "middleName");
        String phone = getStringParameter(request, "phone");

        if (firstName == null || lastName == null || phone == null) {
            setErrorMessage(request, "Заполните обязательные поля");
            redirectTo(response, "/clients/edit/" + clientId);
            return;
        }

        boolean updated = getAutoService().updateClient(clientId, firstName, lastName, middleName, phone);

        if (updated) {
            setSuccessMessage(request, "Данные клиента обновлены");
        } else {
            setErrorMessage(request, "Клиент не найден");
        }

        redirectTo(response, "/clients");
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long clientId = getLongParameter(request, "clientId");
        if (clientId == null) {
            setErrorMessage(request, "Неверный ID клиента");
            redirectTo(response, "/clients");
            return;
        }

        boolean deleted = getAutoService().deleteClient(clientId);

        if (deleted) {
            setSuccessMessage(request, "Клиент удален");
        } else {
            setErrorMessage(request, "Клиент не найден");
        }

        redirectTo(response, "/clients");
    }
}