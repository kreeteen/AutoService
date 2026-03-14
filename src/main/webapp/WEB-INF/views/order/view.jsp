<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Просмотр заказа" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Информация о заказе #${order.orderId}</h2>

<div class="order-info">
    <p><strong>Статус:</strong>
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
    </p>
    <p><strong>Дата создания:</strong> ${order.createdDate}</p>
    <p><strong>Дата завершения:</strong> ${order.completionDate}</p>
    <p><strong>Описание работ:</strong> ${order.description}</p>
    <p><strong>Описание ремонта:</strong> ${order.repairDescription}</p>
</div>

<h3>Назначенные сотрудники</h3>
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
                            <c:set var="employee" value="${employeeMap[assignment.employeeId]}"/>
                            <c:choose>
                                <c:when test="${not empty employee}">
                                    ${employee.lastName} ${employee.firstName}
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray; font-style: italic;">
                                        ID: ${assignment.employeeId}
                                        <c:if test="${empty assignment.employeeId}">
                                            (не указан)
                                        </c:if>
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${not empty employee}">
                                ${employee.position}
                            </c:if>
                            <c:if test="${empty employee and not empty assignment.employeeId}">
                                <span style="color: gray; font-style: italic;">не найден</span>
                            </c:if>
                        </td>
                        <td>${assignment.role}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/employees/view/${assignment.employeeId}"
                               class="btn">Просмотр</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Нет назначенных сотрудников</p>
    </c:otherwise>
</c:choose>

<h3>Использованные запчасти</h3>
<c:choose>
    <c:when test="${not empty parts}">
        <table>
            <thead>
                <tr>
                    <th>Название</th>
                    <th>Номер</th>
                    <th>Количество</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="part" items="${parts}">
                    <tr>
                        <td>${part.partName}</td>
                        <td>${part.partNumber}</td>
                        <td>${part.quantity}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>Запчасти не использовались</p>
    </c:otherwise>
</c:choose>

<div class="actions" style="margin-top: 20px;">
    <c:if test="${order.status != 'COMPLETED'}">
        <form action="${pageContext.request.contextPath}/orders" method="post"
              style="display: inline-block; margin-right: 10px;">
            <input type="hidden" name="action" value="updateStatus">
            <input type="hidden" name="orderId" value="${order.orderId}">
            <select name="status" style="display: inline-block;">
                <option value="Принят">Принят</option>
                <option value="В работе">В работе</option>
                <option value="Выполнен">Выполнен</option>
            </select>
            <button type="submit" class="btn">Изменить статус</button>
        </form>

        <c:if test="${order.status != 'COMPLETED'}">
            <form action="${pageContext.request.contextPath}/orders" method="post"
                  style="display: inline-block; margin-right: 10px;">
                <input type="hidden" name="action" value="complete">
                <input type="hidden" name="orderId" value="${order.orderId}">
                <input type="text" name="repairDescription" placeholder="Описание ремонта"
                       style="width: 200px; display: inline-block;">
                <button type="submit" class="btn btn-success">Завершить заказ</button>
            </form>
        </c:if>
    </c:if>

    <a href="${pageContext.request.contextPath}/orders/edit/${order.orderId}" class="btn">Редактировать</a>
    <a href="${pageContext.request.contextPath}/orders" class="btn">Назад к списку</a>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>