   <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
    <div style="margin-top: 40px; padding: 20px; border-top: 1px solid #ccc; color: #666; text-align: center;">
        <p>Режим работы:
            <c:choose>
                <c:when test="${pageContext.servletContext.getAttribute('storageType') == 'JDBC'}">
                    База данных
                </c:when>
                <c:otherwise>
                    Память
                </c:otherwise>
            </c:choose>
        </p>
    </div>
</div>
</body>
</html>