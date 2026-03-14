<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Создать назначение" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Назначить сотрудника на заказ</h2>

<form action="${pageContext.request.contextPath}/assignments" method="post">
    <input type="hidden" name="action" value="create">

    <div class="form-group">
        <label for="employeeId">Сотрудник *</label>
        <select id="employeeId" name="employeeId" required>
            <option value="">Выберите сотрудника</option>
            <c:forEach var="employee" items="${employees}">
                <option value="${employee.employeeId}">
                    ${employee.lastName} ${employee.firstName} (${employee.position})
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="orderId">Заказ *</label>
        <select id="orderId" name="orderId" required>
            <option value="">Выберите заказ</option>
            <c:forEach var="order" items="${orders}">
                <option value="${order.orderId}">
                    Заказ #${order.orderId} (${order.description})
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="role">Роль в заказе *</label>
        <input type="text" id="role" name="role" required placeholder="МЕХАНИК, МЕНЕДЖЕР и т.д.">
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Назначить</button>
        <a href="${pageContext.request.contextPath}/assignments" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>