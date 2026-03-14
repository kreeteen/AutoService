<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="title" value="Главная страница" scope="request"/>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="stats">
    <h2>Статистика</h2>
    <div class="stat-cards">
        <div class="stat-card">
            <h3>${clientsCount}</h3>
            <p>Клиентов</p>
            <a href="${pageContext.request.contextPath}/clients" class="btn">Управление</a>
        </div>
        <div class="stat-card">
            <h3>${employeesCount}</h3>
            <p>Сотрудников</p>
            <a href="${pageContext.request.contextPath}/employees" class="btn">Управление</a>
        </div>
        <div class="stat-card">
            <h3>${carsCount}</h3>
            <p>Автомобилей</p>
            <a href="${pageContext.request.contextPath}/cars" class="btn">Управление</a>
        </div>
        <div class="stat-card">
            <h3>${ordersCount}</h3>
            <p>Заказов</p>
            <a href="${pageContext.request.contextPath}/orders" class="btn">Управление</a>
        </div>
    </div>
</div>

<div class="recent-orders">
    <h2>Последние заказы</h2>
    <c:choose>
        <c:when test="${not empty recentOrders}">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Автомобиль</th>
                        <th>Описание</th>
                        <th>Статус</th>
                        <th>Дата создания</th>
                        <th>Действия</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${recentOrders}">
                        <tr>
                            <td>${order.orderId}</td>
                            <td>
                                <c:forEach var="car" items="${allCars}">
                                    <c:if test="${car.carId == order.carId}">
                                        ${car.brand} ${car.model} (${car.licensePlate})
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${order.description}</td>
                            <td>
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
                            </td>
                            <td>${order.createdDate}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/orders/view/${order.orderId}"
                                   class="btn">Просмотр</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="text-align: center; margin-top: 15px;">
                <a href="${pageContext.request.contextPath}/orders" class="btn">Все заказы →</a>
            </div>
        </c:when>
        <c:otherwise>
            <p>Заказы не найдены.</p>
        </c:otherwise>
    </c:choose>
</div>

<div class="quick-actions">
    <h2>Быстрые действия</h2>
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/orders/new" class="btn btn-success">Создать заказ</a>
        <a href="${pageContext.request.contextPath}/clients/new" class="btn btn-success">Добавить клиента</a>
        <a href="${pageContext.request.contextPath}/cars/new" class="btn btn-success">Добавить автомобиль</a>
        <a href="${pageContext.request.contextPath}/employees/new" class="btn btn-success">Добавить сотрудника</a>
    </div>
</div>

<style>
    .stats {
        margin: 30px 0;
    }
    .stat-cards {
        display: flex;
        gap: 20px;
        flex-wrap: wrap;
    }
    .stat-card {
        flex: 1;
        min-width: 200px;
        background: #f8f9fa;
        padding: 20px;
        border-radius: 8px;
        text-align: center;
        border: 1px solid #dee2e6;
    }
    .stat-card h3 {
        font-size: 32px;
        margin: 10px 0;
        color: #007bff;
    }
    .stat-card p {
        color: #6c757d;
        margin-bottom: 15px;
    }
    .recent-orders, .quick-actions {
        margin: 40px 0;
    }
    .action-buttons {
        display: flex;
        gap: 15px;
        flex-wrap: wrap;
    }
</style>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>