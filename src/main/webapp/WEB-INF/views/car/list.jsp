<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Автомобили" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/cars/new" class="btn btn-success">Добавить автомобиль</a>
    <a href="${pageContext.request.contextPath}/cars/byLicensePlate" class="btn">Поиск по госномеру</a>
</div>

<c:choose>
    <c:when test="${not empty cars}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Госномер</th>
                    <th>Марка</th>
                    <th>Модель</th>
                    <th>Клиент ID</th>
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
                        <td>${car.clientId}</td>
                        <td>${car.manufactureDate.year}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cars/view/${car.carId}"
                               class="btn">Просмотр</a>
                            <a href="${pageContext.request.contextPath}/cars/edit/${car.carId}"
                               class="btn">Редактировать</a>
                            <form action="${pageContext.request.contextPath}/cars" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="carId" value="${car.carId}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="return confirm('Удалить автомобиль?')">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Автомобили не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>