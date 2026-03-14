<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Добавить сотрудника" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<h2>Добавить нового сотрудника</h2>

<form action="${pageContext.request.contextPath}/employees" method="post">
    <input type="hidden" name="action" value="create">

    <div class="form-group">
        <label for="firstName">Имя *</label>
        <input type="text" id="firstName" name="firstName" required>
    </div>

    <div class="form-group">
        <label for="lastName">Фамилия *</label>
        <input type="text" id="lastName" name="lastName" required>
    </div>

    <div class="form-group">
        <label for="middleName">Отчество</label>
        <input type="text" id="middleName" name="middleName">
    </div>

    <div class="form-group">
        <label for="address">Адрес *</label>
        <input type="text" id="address" name="address" required>
    </div>

    <div class="form-group">
        <label for="dateOfBirth">Дата рождения *</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" required>
    </div>

    <div class="form-group">
        <label for="phone">Телефон *</label>
        <input type="tel" id="phone" name="phoneNumber" required>
    </div>

    <div class="form-group">
        <label for="position">Должность *</label>
        <input type="text" id="position" name="position" required>
    </div>

    <div class="form-group">
        <label for="salary">Зарплата *</label>
        <input type="number" id="salary" name="salary" step="0.01" required>
    </div>

    <div class="form-group">
        <label for="experience">Опыт (лет) *</label>
        <input type="number" id="experience" name="experience" required>
    </div>

    <div class="form-group">
        <label for="workSchedule">График работы *</label>
        <input type="text" id="workSchedule" name="workSchedule" required placeholder="9:00-18:00">
    </div>

    <div class="form-group">
        <label for="seniorityBonus">Надбавка за стаж</label>
        <input type="number" id="seniorityBonus" name="seniorityBonus" step="0.01" value="0">
    </div>

    <div class="form-group">
        <button type="submit" class="btn btn-success">Создать</button>
        <a href="${pageContext.request.contextPath}/employees" class="btn">Отмена</a>
    </div>
</form>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>