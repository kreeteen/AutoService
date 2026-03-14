package autoservice.web.controller;

import autoservice.domain.service.AutoServiceFacade;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected AutoServiceFacade getAutoService() {
        return (AutoServiceFacade) getServletContext().getAttribute("autoService");
    }

    protected void forwardToJsp(String jspPath, HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/" + jspPath).forward(request, response);
    }

    protected void redirectTo(HttpServletResponse response, String path)
            throws IOException {
        response.sendRedirect(getServletContext().getContextPath() + path);
    }

    protected Long getLongParameter(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.trim().isEmpty()) {
            try {
                return Long.parseLong(paramValue);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    protected String getStringParameter(HttpServletRequest request, String paramName) {
        String paramValue = request.getParameter(paramName);
        return paramValue != null ? paramValue.trim() : null;
    }

    protected void setSuccessMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("success", message);
    }

    protected void setErrorMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("error", message);
    }
}