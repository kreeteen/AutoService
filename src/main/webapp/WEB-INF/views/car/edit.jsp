<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Редактировать автомобиль" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Редактировать автомобиль</h2>

<form action="${pageContext.request.contextPath}/cars" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="carId" value="${car.carId}">

    <div class="form-group">
        <label for="clientId">Клиент *</label>
        <select id="clientId" name="clientId" required>
            <c:forEach var="client" items="${clients}">
                <option value="${client.clientId}"
                        ${client.clientId == car.clientId ? 'selected' : ''}>
                    ${client.lastName} ${client.firstName} (${client.phone})
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="licensePlate">Госномер *</label>
        <input type="text" id="licensePlate" name="licensePlate"
               value="${car.licensePlate}" required>
    </div>

    <div class="form-group">
        <label for="brand">Марка *</label>
        <input type="text" id="brand" name="brand" value="${car.brand}" required>
    </div>

    <div class="form-group">
        <label for="model">Модель *</label>
        <input type="text" id="model" name="model" value="${car.model}" required>
    </div>

    <div class="form-group">
        <label for="manufactureDate">Год выпуска *</label>
        <input type="date" id="manufactureDate" name="manufactureDate"
               value="${car.manufactureDate}" required>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Сохранить</button>
        <a href="${pageContext.request.contextPath}/cars" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>