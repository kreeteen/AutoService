<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Запчасти" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="actions">
    <a href="${pageContext.request.contextPath}/parts/new" class="btn btn-success">Добавить запчасть</a>
    <a href="${pageContext.request.contextPath}/parts/byOrder" class="btn">Поиск по заказу</a>
</div>

<c:choose>
    <c:when test="${not empty parts}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Заказ ID</th>
                    <th>Название</th>
                    <th>Номер</th>
                    <th>Количество</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="part" items="${parts}">
                    <tr>
                        <td>${part.replacementId}</td>
                        <td>${part.orderId}</td>
                        <td>${part.partName}</td>
                        <td>${part.partNumber}</td>
                        <td>${part.quantity}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/orders/view/${part.orderId}"
                               class="btn">Просмотр заказа</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Запчасти не найдены.</p>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>