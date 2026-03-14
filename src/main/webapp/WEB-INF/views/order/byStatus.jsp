<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Поиск заказов по статусу" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Поиск заказов по статусу</h2>

<form action="${pageContext.request.contextPath}/orders/byStatus" method="get" class="search-form">
    <div class="form-group" style="display: flex; gap: 10px;">
        <select name="status" style="flex: 1;">
            <option value="">Все статусы</option>
            <option value="Принят" ${searchStatus == 'Принят' ? 'selected' : ''}>Принят</option>
            <option value="В работе" ${searchStatus == 'В работе' ? 'selected' : ''}>В работе</option>
            <option value="Выполнен" ${searchStatus == 'Выполнен' ? 'selected' : ''}>Выполнен</option>
        </select>
        <button type="submit" class="btn">Поиск</button>
        <a href="${pageContext.request.contextPath}/orders" class="btn">Сбросить</a>
    </div>
</form>

<c:if test="${not empty searchStatus}">
    <h3>Результаты поиска:
        <c:choose>
            <c:when test="${searchStatus == 'Принят'}">Принят</c:when>
            <c:when test="${searchStatus == 'В работе'}">В работе</c:when>
            <c:when test="${searchStatus == 'Выполнен'}">Выполнен</c:when>
        </c:choose>
    </h3>
</c:if>

<c:choose>
    <c:when test="${not empty orders}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ID Авто</th>
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
                        <td>${order.carId}</td>
                        <td>${order.description}</td>
                        <td>
                            <c:choose>
                                <c:when test="${order.status == 'Выполнен'}">
                                    <span style="color: green;">Выполнен</span>
                                </c:when>
                                <c:when test="${order.status == 'В работе'}">
                                    <span style="color: orange;">В работе</span>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray;">Принят</span>
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
        <c:if test="${not empty searchStatus}">
            <p>Заказы со статусом
                <c:choose>
                    <c:when test="${searchStatus == 'Принят'}">"Принят"</c:when>
                    <c:when test="${searchStatus == 'В работе'}">"В работе"</c:when>
                    <c:when test="${searchStatus == 'Выполнен'}">"Выполнен"</c:when>
                </c:choose>
                не найдены.
            </p>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>