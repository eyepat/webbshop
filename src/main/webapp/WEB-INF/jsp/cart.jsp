<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>Korg</title>
    <style>
        body { font-family: system-ui, Arial, sans-serif; margin: 0; }
        .topbar { display:flex; justify-content:space-between; align-items:center;
            padding:12px 18px; border-bottom:1px solid #e5e5e5; position:sticky; top:0; background:#fff; }
        .cart-link { font-size:22px; text-decoration:none; }
        .wrap { max-width:900px; margin:30px auto; padding:0 16px; }
        table { width:100%; border-collapse:collapse; font-size:16px; }
        th, td { border:1px solid #e5e5e5; padding:10px; }
        th { background:#fafafa; text-align:left; font-size:17px; }
        .btn { padding:8px 12px; border:1px solid #333; background:#fff; border-radius:8px; cursor:pointer; }
        .actions { margin-top:12px; }
    </style>
</head>
<body>

<div class="topbar">
    <div class="user">
        <c:choose>
            <c:when test="${not empty sessionScope.authUser}">
                👤 Inloggad som <b>${sessionScope.authUser.username}</b>
                &nbsp;|&nbsp; <a href="<c:url value='/app?action=logout'/>">Logga ut</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/app?action=loginForm'/>">Logga in</a>
            </c:otherwise>
        </c:choose>
    </div>
    <a class="cart-link" href="<c:url value='/app?action=viewCart'/>" title="Korg">🛒</a>
</div>

<div class="wrap">
    <c:set var="cart" value="${requestScope.cart}" />
    <c:choose>
        <c:when test="${empty cart or empty cart.lines}">
            <p style="text-align:center;">Korgen är tom.</p>
            <p style="text-align:center;">
                <a href="<c:url value='/app?action=listItems'/>" class="btn">Handla</a>
            </p>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Namn</th><th>Pris</th><th>Antal</th><th>Rad-summa</th><th>Ändra</th><th>Ta bort</th>
                </tr>
                <c:forEach var="line" items="${cart.lines}">
                    <tr>
                        <td>${line.name}</td>
                        <td>${line.price}</td>
                        <td>${line.qty}</td>
                        <td>${line.lineTotal}</td>
                        <td>
                            <form method="post" action="<c:url value='/app'/>" style="display:inline">
                                <input type="hidden" name="action" value="updateQty"/>
                                <input type="hidden" name="itemId" value="${line.itemId}"/>
                                <input type="number" name="qty" value="${line.qty}" min="0" style="width:70px"/>
                                <button type="submit" class="btn">Uppdatera</button>
                            </form>
                        </td>
                        <td>
                            <c:url var="removeUrl" value="/app">
                                <c:param name="action" value="remove"/>
                                <c:param name="itemId" value="${line.itemId}"/>
                            </c:url>
                            <a href="${removeUrl}" class="btn">X</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3" align="right"><b>Total:</b></td>
                    <td colspan="3"><b>${cart.total}</b></td>
                </tr>
            </table>

            <div class="actions">
                <a href="<c:url value='/app?action=listItems'/>" class="btn">Fortsätt handla</a>
                &nbsp;
                <a href="<c:url value='/app?action=clear'/>" class="btn">Töm korg</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
