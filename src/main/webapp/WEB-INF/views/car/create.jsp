<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Добавить автомобиль" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Добавить новый автомобиль</h2>

<form action="${pageContext.request.contextPath}/cars" method="post">
    <input type="hidden" name="action" value="create">

    <div class="form-group">
        <label for="clientId">Клиент *</label>
        <select id="clientId" name="clientId" required>
            <option value="">Выберите клиента</option>
            <c:forEach var="client" items="${clients}">
                <option value="${client.clientId}">
                    ${client.lastName} ${client.firstName} (${client.phone})
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="licensePlate">Госномер *</label>
        <input type="text" id="licensePlate" name="licensePlate" required
               placeholder="A123BC777">
        <small>Формат: буква, 3 цифры, 2 буквы, 3 цифры</small>
    </div>

    <div class="form-group">
        <label for="brand">Марка *</label>
        <input type="text" id="brand" name="brand" required>
    </div>

    <div class="form-group">
        <label for="model">Модель *</label>
        <input type="text" id="model" name="model" required>
    </div>

    <div class="form-group">
        <label for="manufactureDate">Год выпуска *</label>
        <input type="date" id="manufactureDate" name="manufactureDate" required>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Создать</button>
        <a href="${pageContext.request.contextPath}/cars" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>