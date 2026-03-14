<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Добавить запчасть" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Добавить запчасть к заказу</h2>

<form action="${pageContext.request.contextPath}/parts" method="post">
    <input type="hidden" name="action" value="create">

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
        <label for="partName">Название запчасти *</label>
        <input type="text" id="partName" name="partName" required>
    </div>

    <div class="form-group">
        <label for="partNumber">Номер запчасти *</label>
        <input type="text" id="partNumber" name="partNumber" required>
    </div>

    <div class="form-group">
        <label for="quantity">Количество *</label>
        <input type="number" id="quantity" name="quantity" min="1" value="1" required>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Добавить</button>
        <a href="${pageContext.request.contextPath}/parts" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>