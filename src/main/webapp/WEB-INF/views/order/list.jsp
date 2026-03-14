<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Заказы" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/orders/new" class="btn btn-success">Создать заказ</a>
    <a href="${pageContext.request.contextPath}/orders/byStatus" class="btn">Поиск по статусу</a>
</div>

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
                    <th>Дата завершения</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty order.carId}">
                                    ${order.carId}
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray; font-style: italic;">Не указано</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
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
                        <td>${order.completionDate}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/view/${order.orderId}"
                               class="btn">Просмотр</a>
                            <a href="${pageContext.request.contextPath}/orders/edit/${order.orderId}"
                               class="btn">Редактировать</a>
                            <form action="${pageContext.request.contextPath}/orders" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="orderId" value="${order.orderId}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="return confirm('Удалить заказ?')">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Заказы не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>