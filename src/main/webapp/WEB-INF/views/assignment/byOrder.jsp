<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Назначения по заказу" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Поиск назначений по заказу</h2>

<form action="${pageContext.request.contextPath}/assignments/byOrder" method="get" class="search-form">
    <div class="form-group" style="display: flex; gap: 10px;">
        <input type="number" name="orderId" placeholder="Введите ID заказа"
               value="${searchOrderId}" style="flex: 1;">
        <button type="submit" class="btn">Поиск</button>
        <a href="${pageContext.request.contextPath}/assignments" class="btn">Сбросить</a>
    </div>
</form>

<c:if test="${not empty searchOrderId}">
    <h3>Результаты поиска для заказа #${searchOrderId}</h3>
</c:if>

<c:choose>
    <c:when test="${not empty assignments}">
        <table>
            <thead>
                <tr>
                    <th>Сотрудник</th>
                    <th>Должность</th>
                    <th>Роль в заказе</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>
                            <c:forEach var="employee" items="${allEmployees}">
                                <c:if test="${employee.employeeId == assignment.employeeId}">
                                    ${employee.lastName} ${employee.firstName}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>
                            <c:forEach var="employee" items="${allEmployees}">
                                <c:if test="${employee.employeeId == assignment.employeeId}">
                                    ${employee.position}
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${assignment.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employees/view/${assignment.employeeId}"
                               class="btn">Просмотр сотрудника</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty searchOrderId}">
            <p>Назначения для заказа #${searchOrderId} не найдены.</p>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>