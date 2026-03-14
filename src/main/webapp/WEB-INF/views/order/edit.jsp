<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Редактировать заказ" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Редактировать заказ</h2>

<form action="${pageContext.request.contextPath}/orders" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="orderId" value="${order.orderId}">

    <div class="form-group">
        <label for="carId">Автомобиль *</label>
        <select id="carId" name="carId" required>
            <c:forEach var="car" items="${cars}">
                <option value="${car.carId}" ${car.carId == order.carId ? 'selected' : ''}>
                    ${car.brand} ${car.model} (${car.licensePlate})
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="description">Описание работ *</label>
        <textarea id="description" name="description" rows="4" required>${order.description}</textarea>
    </div>

    <div class="form-group">
        <label for="status">Статус *</label>
        <select id="status" name="status" required>
            <option value="Принят" ${order.status == 'Принят' ? 'selected' : ''}>Принят</option>
            <option value="В работе" ${order.status == 'IN_PROGRESS' ? 'selected' : ''}>В работе</option>
            <option value="Выполнен" ${order.status == 'Выполнен' ? 'selected' : ''}>Выполнен</option>
        </select>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Сохранить</button>
        <a href="${pageContext.request.contextPath}/orders" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>