<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Korg</title></head>
<body>
<c:set var="cart" value="${requestScope.cart}" />

<c:choose>
    <c:when test="${empty cart.lines}">
        <p>Korgen är tom.</p>
        <c:url var="shopUrl" value="/app">
            <c:param name="action" value="listItems"/>
        </c:url>
        <p>
            <a href="${shopUrl}" style="display:inline-block;padding:8px 12px;border:1px solid #333;text-decoration:none;">
                Handla
            </a>
        </p>
    </c:when>

    <c:otherwise>
        <table border="1" cellpadding="6" cellspacing="0">
            <tr>
                <th>Namn</th><th>Pris</th><th>Antal</th><th>Rad-summa</th><th>Ändra</th><th>Ta bort rad</th>
            </tr>

            <c:forEach var="line" items="${cart.lines}">
                <tr>
                    <td>${line.name}</td>
                    <td>${line.price}</td>
                    <td>${line.qty}</td>
                    <td>${line.lineTotal}</td>

                    <!-- Ändra till exakt antal -->
                    <td>
                        <form method="post" action="<c:url value='/app'/>" style="display:inline">
                            <input type="hidden" name="action" value="updateQty"/>
                            <input type="hidden" name="itemId" value="${line.itemId}"/>
                            <input type="number" name="qty" value="${line.qty}" min="0" style="width:60px"/>
                            <button type="submit">Uppdatera</button>
                        </form>
                    </td>

                    <!-- Ta bort hela raden -->
                    <td>
                        <c:url var="removeUrl" value="/app">
                            <c:param name="action" value="remove"/>
                            <c:param name="itemId" value="${line.itemId}"/>
                        </c:url>
                        <a href="${removeUrl}">X</a>
                    </td>
                </tr>
            </c:forEach>

            <tr>
                <td colspan="3" align="right"><b>Total:</b></td>
                <td colspan="3"><b>${cart.total}</b></td>
            </tr>
        </table>

        <p>
            <a href="<c:url value='/app?action=listItems'/>">Fortsätt handla</a> |
            <a href="<c:url value='/app?action=clear'/>">Töm korg</a>
        </p>
    </c:otherwise>
</c:choose>
</body>
</html>
