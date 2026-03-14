<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Редактировать клиента" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Редактировать клиента</h2>

<form action="${pageContext.request.contextPath}/clients" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="clientId" value="${client.clientId}">

    <div class="form-group">
        <label for="firstName">Имя *</label>
        <input type="text" id="firstName" name="firstName" value="${client.firstName}" required>
    </div>

    <div class="form-group">
        <label for="lastName">Фамилия *</label>
        <input type="text" id="lastName" name="lastName" value="${client.lastName}" required>
    </div>

    <div class="form-group">
        <label for="middleName">Отчество</label>
        <input type="text" id="middleName" name="middleName" value="${client.middleName}">
    </div>

    <div class="form-group">
        <label for="phone">Телефон *</label>
        <input type="tel" id="phone" name="phone" value="${client.phone}" required>
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Сохранить</button>
        <a href="${pageContext.request.contextPath}/clients" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>