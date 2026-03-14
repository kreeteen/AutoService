<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Ошибка сервера" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div style="text-align: center; padding: 50px;">
    <h1 style="color: #dc3545; font-size: 48px;">500</h1>
    <h2>Внутренняя ошибка сервера</h2>
    <p>Произошла непредвиденная ошибка. Мы уже работаем над её устранением.</p>

    <c:if test="${not empty pageContext.exception}">
        <div style="background: #f8f9fa; padding: 20px; border-radius: 5px; margin: 20px 0; text-align: left;">
            <p><strong>Ошибка:</strong> ${pageContext.exception.message}</p>
        </div>
    </c:if>

    <div style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
        <button onclick="location.reload()" class="btn">Обновить страницу</button>
        <a href="javascript:history.back()" class="btn">Назад</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>