<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Назначения по сотруднику" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Поиск назначений по сотруднику</h2>

<form action="${pageContext.request.contextPath}/assignments/byEmployee" method="get" class="search-form">
    <div class="form-group" style="display: flex; gap: 10px;">
        <input type="number" name="employeeId" placeholder="Введите ID сотрудника"
               value="${searchEmployeeId}" style="flex: 1;">
        <button type="submit" class="btn">Поиск</button>
        <a href="${pageContext.request.contextPath}/assignments" class="btn">Сбросить</a>
    </div>
</form>

<c:if test="${not empty searchEmployeeId}">
    <h3>Результаты поиска для сотрудника #${searchEmployeeId}</h3>
</c:if>

<c:choose>
    <c:when test="${not empty assignments}">
        <table>
            <thead>
                <tr>
                    <th>Заказ</th>
                    <th>Описание заказа</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>Заказ #${assignment.orderId}</td>
                        <td>
                            <c:forEach var="order" items="${allOrders}">
                                <c:if test="${order.orderId == assignment.orderId}">
                                    ${order.description}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${assignment.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/view/${assignment.orderId}"
                               class="btn">Просмотр заказа</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty searchEmployeeId}">
            <p>Назначения для сотрудника #${searchEmployeeId} не найдены.</p>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>