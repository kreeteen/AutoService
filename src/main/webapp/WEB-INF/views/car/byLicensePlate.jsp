<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Поиск автомобилей по госномеру" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Поиск автомобилей по госномеру</h2>

<form action="${pageContext.request.contextPath}/cars/byLicensePlate" method="get" class="search-form">
    <div class="form-group" style="display: flex; gap: 10px;">
        <input type="text" name="licensePlate" placeholder="Введите госномер"
               value="${searchLicensePlate}" style="flex: 1;">
        <button type="submit" class="btn">Поиск</button>
        <a href="${pageContext.request.contextPath}/cars" class="btn">Сбросить</a>
    </div>
</form>

<c:if test="${not empty searchLicensePlate}">
    <h3>Результаты поиска: "${searchLicensePlate}"</h3>
</c:if>

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
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty searchLicensePlate}">
            <p>Автомобили с госномером "${searchLicensePlate}" не найдены.</p>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>