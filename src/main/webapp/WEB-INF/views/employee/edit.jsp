<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Редактировать сотрудника" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Редактировать сотрудника</h2>

<form action="${pageContext.request.contextPath}/employees" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="employeeId" value="${employee.employeeId}">

    <div class="form-group">
        <label for="firstName">Имя *</label>
        <input type="text" id="firstName" name="firstName" value="${employee.firstName}" required>
    </div>

    <div class="form-group">
        <label for="lastName">Фамилия *</label>
        <input type="text" id="lastName" name="lastName" value="${employee.lastName}" required>
    </div>

    <div class="form-group">
        <label for="middleName">Отчество</label>
        <input type="text" id="middleName" name="middleName" value="${employee.middleName}">
    </div>

    <div class="form-group">
        <label for="address">Адрес *</label>
        <input type="text" id="address" name="address" value="${employee.address}" required>
    </div>

    <div class="form-group">
        <label for="dateOfBirth">Дата рождения *</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth"
               value="${employee.dateOfBirth}" required>
    </div>

    <div class="form-group">
        <label for="phone">Телефон *</label>
        <input type="tel" id="phone" name="phoneNumber" value="${employee.phoneNumber}" required>
    </div>

    <div class="form-group">
        <label for="position">Должность *</label>
        <input type="text" id="position" name="position" value="${employee.position}" required>
    </div>

    <div class="form-group">
        <label for="salary">Зарплата *</label>
        <input type="number" id="salary" name="salary" value="${employee.salary}" step="0.01" required>
    </div>

    <div class="form-group">
        <label for="experience">Опыт (лет) *</label>
        <input type="number" id="experience" name="experience" value="${employee.experience}" required>
    </div>

    <div class="form-group">
        <label for="workSchedule">График работы *</label>
        <input type="text" id="workSchedule" name="workSchedule"
               value="${employee.workSchedule}" required>
    </div>

    <div class="form-group">
        <label for="seniorityBonus">Надбавка за стаж</label>
        <input type="number" id="seniorityBonus" name="seniorityBonus"
               value="${employee.seniorityBonus}" step="0.01">
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Сохранить</button>
        <a href="${pageContext.request.contextPath}/employees" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>