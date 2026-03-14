<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Автосервис - ${param.title}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .navbar { background: #333; padding: 10px; margin-bottom: 20px; border-radius: 5px; }
        .navbar a { color: white; margin-right: 15px; text-decoration: none; padding: 5px 10px; border-radius: 3px; }
        .navbar a:hover { background: #555; }
        .alert { padding: 10px; margin: 10px 0; border-radius: 4px; }
        .alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .btn { display: inline-block; padding: 6px 12px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; }
        .btn:hover { background: #0056b3; }
        .btn-danger { background: #dc3545; }
        .btn-danger:hover { background: #c82333; }
        .btn-success { background: #28a745; }
        .btn-success:hover { background: #218838; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select, textarea { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .actions { margin: 20px 0; }
    </style>
</head>
<body>
<div class="container">
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/">Главная</a>
        <a href="${pageContext.request.contextPath}/clients">Клиенты</a>
        <a href="${pageContext.request.contextPath}/employees">Сотрудники</a>
        <a href="${pageContext.request.contextPath}/cars">Автомобили</a>
        <a href="${pageContext.request.contextPath}/orders">Заказы</a>
        <a href="${pageContext.request.contextPath}/assignments">Назначения</a>
        <a href="${pageContext.request.contextPath}/parts">Запчасти</a>
    </div>

    <h1>${param.title}</h1>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>