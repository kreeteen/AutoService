<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Страница не найдена" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div style="text-align: center; padding: 50px;">
    <h1 style="color: #dc3545; font-size: 48px;">404</h1>
    <h2>Страница не найдена</h2>
    <p>Запрошенная страница не существует или была перемещена.</p>
    <div style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/" class="btn">На главную</a>
        <a href="javascript:history.back()" class="btn">Назад</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>