<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Запчасти по заказу" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Поиск запчастей по заказу</h2>

<form action="${pageContext.request.contextPath}/parts/byOrder" method="get" class="search-form">
    <div class="form-group" style="display: flex; gap: 10px;">
        <input type="number" name="orderId" placeholder="Введите ID заказа"
               value="${searchOrderId}" style="flex: 1;">
        <button type="submit" class="btn">Поиск</button>
        <a href="${pageContext.request.contextPath}/parts" class="btn">Сбросить</a>
    </div>
</form>

<c:if test="${not empty searchOrderId}">
    <h3>Результаты поиска для заказа #${searchOrderId}</h3>
</c:if>

<c:choose>
    <c:when test="${not empty parts}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Номер</th>
                    <th>Количество</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="part" items="${parts}">
                    <tr>
                        <td>${part.replacementId}</td>
                        <td>${part.partName}</td>
                        <td>${part.partNumber}</td>
                        <td>${part.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <c:if test="${not empty searchOrderId}">
            <p>Запчасти для заказа #${searchOrderId} не найдены.</p>
        </c:if>
    </c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>