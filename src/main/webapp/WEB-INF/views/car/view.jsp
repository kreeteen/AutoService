<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Просмотр автомобиля" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Информация об автомобиле</h2>

<div class="car-info">
    <p><strong>ID:</strong> ${car.carId}</p>
    <p><strong>Госномер:</strong> ${car.licensePlate}</p>
    <p><strong>Марка:</strong> ${car.brand}</p>
    <p><strong>Модель:</strong> ${car.model}</p>
    <p><strong>Год выпуска:</strong> ${car.manufactureDate.year}</p>
    <p><strong>Клиент ID:</strong> ${car.clientId}</p>
</div>

<h3>История заказов</h3>
<c:choose>
    <c:when test="${not empty orders}">
        <table>
            <thead>
                <tr>
                    <th>ID заказа</th>
                    <th>Описание</th>
                    <th>Статус</th>
                    <th>Дата создания</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'COMPLETED'}">
                                    <span style="color: green;">Завершен</span>
                                </c:when>
                                <c:when test="${order.status == 'IN_PROGRESS'}">
                                    <span style="color: orange;">В работе</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray;">Ожидает</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${order.createdDate}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/view/${order.orderId}"
                               class="btn">Просмотр</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Заказов для этого автомобиля нет</p>
    </c:otherwise>
</c:choose>

<div class="actions" style="margin-top: 20px;">
    <form action="${pageContext.request.contextPath}/cars" method="post"
          style="display: inline-block; margin-right: 10px;">
        <input type="hidden" name="action" value="updateLicensePlate">
        <input type="hidden" name="carId" value="${car.carId}">
        <input type="text" name="newLicensePlate" placeholder="Новый госномер"
               style="width: 150px; display: inline-block;">
        <button type="submit" class="btn">Обновить номер</button>
    </form>

    <a href="${pageContext.request.contextPath}/cars/edit/${car.carId}" class="btn">Редактировать</a>
    <a href="${pageContext.request.contextPath}/cars" class="btn">Назад к списку</a>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>