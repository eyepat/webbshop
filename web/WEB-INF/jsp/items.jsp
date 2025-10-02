<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Produkter</title>
</head>
<body>
<h2>Produkter</h2>

<c:choose>
    <c:when test="${empty items}">
        <p>Inga produkter hittades.</p>
    </c:when>
    <c:otherwise>
        <table border="1" cellpadding="6" cellspacing="0">
            <tr>
                <th>Namn</th>
                <th>Beskrivning</th>
                <th>Pris</th>
                <th>Antal</th>
                <th></th>
            </tr>
            <c:forEach var="p" items="${items}">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.description}</td>
                    <td>${p.price} kr</td>
                    <td>
                        <form method="post" action="<c:url value='/app'/>">
                            <input type="hidden" name="action" value="addToCart"/>
                            <input type="hidden" name="itemId" value="${p.id}"/>
                            <input type="number" name="qty" value="1" min="1"/>
                            <button type="submit">Lägg i korg</button>
                        </form>
                    </td>
                    <td>
                        <!-- Extra knapp om man vill lägga till direkt 1 st -->
                        <form method="post" action="<c:url value='/app'/>" style="display:inline">
                            <input type="hidden" name="action" value="addToCart"/>
                            <input type="hidden" name="itemId" value="${p.id}"/>
                            <input type="hidden" name="qty" value="1"/>
                            <button type="submit">+</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<p>
    <a href="<c:url value='/app?action=viewCart'/>">Visa korg</a>
</p>
</body>
</html>
