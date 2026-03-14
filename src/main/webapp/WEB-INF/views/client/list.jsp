<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Клиенты" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/clients/new" class="btn btn-success">Добавить клиента</a>
</div>

<c:choose>
    <c:when test="${not empty clients}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Отчество</th>
                    <th>Телефон</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="client" items="${clients}">
                    <tr>
                        <td>${client.clientId}</td>
                        <td>${client.lastName}</td>
                        <td>${client.firstName}</td>
                        <td>${client.middleName}</td>
                        <td>${client.phone}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/clients/view/${client.clientId}"
                               class="btn">Просмотр</a>
                            <a href="${pageContext.request.contextPath}/clients/edit/${client.clientId}"
                               class="btn">Редактировать</a>
                            <form action="${pageContext.request.contextPath}/clients" method="post"
                                  style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="clientId" value="${client.clientId}">
                                <button type="submit" class="btn btn-danger"
                                        onclick="return confirm('Удалить клиента?')">Удалить</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Клиенты не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>