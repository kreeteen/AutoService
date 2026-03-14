<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Просмотр сотрудника" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Информация о сотруднике</h2>

<div class="employee-info">
    <p><strong>ID:</strong> ${employee.employeeId}</p>
    <p><strong>Фамилия:</strong> ${employee.lastName}</p>
    <p><strong>Имя:</strong> ${employee.firstName}</p>
    <p><strong>Отчество:</strong> ${employee.middleName}</p>
    <p><strong>Адрес:</strong> ${employee.address}</p>
    <p><strong>Дата рождения:</strong> ${employee.dateOfBirth}</p>
    <p><strong>Телефон:</strong> ${employee.phoneNumber}</p>
    <p><strong>Должность:</strong> ${employee.position}</p>
    <p><strong>Зарплата:</strong> ${employee.salary}</p>
    <p><strong>Опыт:</strong> ${employee.experience} лет</p>
    <p><strong>График работы:</strong> ${employee.workSchedule}</p>
    <p><strong>Надбавка за стаж:</strong> ${employee.seniorityBonus}</p>
</div>

<h3>Текущие назначения</h3>
<c:choose>
    <c:when test="${not empty assignments}">
        <table>
            <thead>
                <tr>
                    <th>ID заказа</th>
                    <th>Роль</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assignment" items="${assignments}">
                    <tr>
                        <td>${assignment.orderId}</td>
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
        <p>Нет текущих назначений</p>
    </c:otherwise>
</c:choose>

<div class="actions" style="margin-top: 20px;">
    <form action="${pageContext.request.contextPath}/employees" method="post"
          style="display: inline-block; margin-right: 10px;">
        <input type="hidden" name="action" value="updateSalary">
        <input type="hidden" name="employeeId" value="${employee.employeeId}">
        <input type="number" name="newSalary" placeholder="Новая зарплата" step="0.01"
               style="width: 150px; display: inline-block;">
        <button type="submit" class="btn">Обновить зарплату</button>
    </form>

    <a href="${pageContext.request.contextPath}/employees/edit/${employee.employeeId}" class="btn">Редактировать</a>
    <a href="${pageContext.request.contextPath}/employees" class="btn">Назад к списку</a>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>