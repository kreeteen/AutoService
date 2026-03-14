package autoservice.web.controller;

import autoservice.domain.model.PartReplacement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PartServlet", urlPatterns = {"/parts", "/parts/*"})
public class PartServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            // Все запчасти
            List<PartReplacement> parts = getAutoService().getAllPartReplacements();
            request.setAttribute("parts", parts);
            forwardToJsp("part/list.jsp", request, response);

        } else if (pathInfo.equals("/new")) {
            // Форма создания записи о запчасти
            request.setAttribute("orders", getAutoService().getAllOrders());
            forwardToJsp("part/create.jsp", request, response);

        } else if (pathInfo.equals("/byOrder")) {
            // Запчасти по заказу
            Long orderId = getLongParameter(request, "orderId");
            if (orderId != null) {
                List<PartReplacement> parts = getAutoService().getOrderPartReplacements(orderId);
                request.setAttribute("parts", parts);
                request.setAttribute("searchOrderId", orderId);
            }
            forwardToJsp("part/byOrder.jsp", request, response);

        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            createPart(request, response);
        } else if ("delete".equals(action)) {
            deletePart(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    private void createPart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        try {
            Long orderId = getLongParameter(request, "orderId");
            String partName = getStringParameter(request, "partName");
            String partNumber = getStringParameter(request, "partNumber");
            Integer quantity = Integer.parseInt(request.getParameter("quantity"));

            if (orderId == null || partName == null || partNumber == null || quantity == null) {
                setErrorMessage(request, "Заполните все обязательные поля");
                redirectTo(response, "/parts/new");
                return;
            }

            PartReplacement part = getAutoService().addPartReplacement(orderId, partName, partNumber, quantity);
            setSuccessMessage(request, "Запчасть добавлена. ID: " + part.getReplacementId());
            redirectTo(response, "/parts");

        } catch (Exception e) {
            setErrorMessage(request, "Ошибка добавления запчасти: " + e.getMessage());
            redirectTo(response, "/parts/new");
        }
    }

    private void deletePart(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long partId = getLongParameter(request, "partId");
        if (partId == null) {
            setErrorMessage(request, "Неверный ID запчасти");
            redirectTo(response, "/parts");
            return;
        }

        // Упрощенная реализация
        setSuccessMessage(request, "Функция удаления запчасти будет реализована в следующей версии");
        redirectTo(response, "/parts");
    }
}