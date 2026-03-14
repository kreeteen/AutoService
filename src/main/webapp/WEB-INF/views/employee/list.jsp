<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Сотрудники" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/employees/new" class="btn btn-success">Добавить сотрудника</a>
    <a href="${pageContext.request.contextPath}/employees/byPosition" class="btn">Поиск по должности</a>
</div>

<c:choose>
    <c:when test="${not empty employees}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Отчество</th>
                    <th>Должность</th>
                    <th>Телефон</th>
                    <th>Зарплата</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="employee" items="${employees}">
                    <tr>
                        <td>${employee.employeeId}</td>
                        <td>${employee.lastName}</td>
                        <td>${employee.firstName}</td>
                        <td>${employee.middleName}</td>
                        <td>${employee.position}</td>
                        <td>${employee.phoneNumber}</td>
                        <td>${employee.salary}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employees/view/${employee.employeeId}" 
                               class="btn">Просмотр</a>
                            <a href="${pageContext.request.contextPath}/employees/edit/${employee.employeeId}" 
                               class="btn">Редактировать</a>
                            <form action="${pageContext.request.contextPath}/employees" method="post" 
                                  style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="employeeId" value="${employee.employeeId}">
                                <button type="submit" class="btn btn-danger" 
                                        onclick="return confirm('Удалить сотрудника?')">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Сотрудники не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>