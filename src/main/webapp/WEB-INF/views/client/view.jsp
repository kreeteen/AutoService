<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Просмотр клиента" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Информация о клиенте</h2>

<div class="client-info">
    <p><strong>ID:</strong> ${client.clientId}</p>
    <p><strong>Фамилия:</strong> ${client.lastName}</p>
    <p><strong>Имя:</strong> ${client.firstName}</p>
    <p><strong>Отчество:</strong> ${client.middleName}</p>
    <p><strong>Телефон:</strong> ${client.phone}</p>
</div>

<h3>Автомобили клиента</h3>
<c:choose>
    <c:when test="${not empty cars}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Госномер</th>
                    <th>Марка</th>
                    <th>Модель</th>
                    <th>Год выпуска</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="car" items="${cars}">
                    <tr>
                        <td>${car.carId}</td>
                        <td>${car.licensePlate}</td>
                        <td>${car.brand}</td>
                        <td>${car.model}</td>
                        <td>${car.manufactureDate.year}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cars/view/${car.carId}"
                               class="btn">Просмотр</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>У клиента нет автомобилей</p>
    </c:otherwise>
</c:choose>

<div class="actions" style="margin-top: 20px;">
    <a href="${pageContext.request.contextPath}/clients/edit/${client.clientId}" class="btn">Редактировать</a>
    <a href="${pageContext.request.contextPath}/clients" class="btn">Назад к списку</a>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>