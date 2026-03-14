<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Все назначения" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/assignments/new" class="btn btn-success">Создать назначение</a>
    <a href="${pageContext.request.contextPath}/assignments/byOrder" class="btn">Поиск по заказу</a>
    <a href="${pageContext.request.contextPath}/assignments/byEmployee" class="btn">Поиск по сотруднику</a>
</div>

<c:choose>
    <c:when test="${not empty assignments}">
        <table>
            <thead>
                <tr>
                    <th>ID сотрудника</th>
                    <th>ID заказа</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>${assignment.employeeId}</td>
                        <td>${assignment.orderId}</td>
                        <td>${assignment.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/view/${assignment.orderId}"
                               class="btn">Просмотр заказа</a>
                            <a href="${pageContext.request.contextPath}/employees/view/${assignment.employeeId}"
                               class="btn">Просмотр сотрудника</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Назначения не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>