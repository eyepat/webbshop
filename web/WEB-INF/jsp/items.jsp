<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Produkter</title>
    <style>
        body { font-family: system-ui, Arial, sans-serif; margin: 0; }
        .topbar { display:flex; justify-content:space-between; align-items:center;
            padding:12px 18px; border-bottom:1px solid #e5e5e5; position:sticky; top:0; background:#fff; }
        .cart-link { position:relative; display:inline-block; font-size:22px; text-decoration:none; }
        .badge { position:absolute; top:-6px; right:-10px; background:#e11d48; color:#fff;
            border-radius:999px; padding:2px 6px; font-size:12px; line-height:1; }
        .page { max-width:1000px; margin:30px auto; padding:0 16px; }
        h2 { text-align:center; margin:10px 0 24px; }
        .grid { display:grid; gap:18px; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); }
        .card { border:1px solid #e5e5e5; border-radius:14px; padding:18px;
            box-shadow:0 2px 10px rgba(0,0,0,0.05); display:flex; flex-direction:column; gap:10px; }
        .title { font-size:20px; font-weight:600; }
        .desc { color:#555; min-height:38px; }
        .price { font-size:18px; font-weight:600; margin-top:auto; }
        .btn { display:inline-block; padding:10px 14px; border:1px solid #333; border-radius:10px;
            background:#fff; cursor:pointer; font-weight:600; }
        .user a { text-decoration:none; }
    </style>
</head>
<body>

<!-- räkna total qty i korgen -->
<c:set var="cartCount" value="0"/>
<c:if test="${not empty sessionScope.cart}">
    <c:forEach var="l" items="${sessionScope.cart.lines}">
        <c:set var="cartCount" value="${cartCount + l.qty}"/>
    </c:forEach>
</c:if>

<div class="topbar">
    <div class="user">
        <c:choose>
            <c:when test="${not empty sessionScope.authUser}">
                👤 Hej, <b>${sessionScope.authUser.username}</b>
                &nbsp;|&nbsp; <a href="<c:url value='/app?action=logout'/>">Logga ut</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/app?action=loginForm'/>">Logga in</a>
            </c:otherwise>
        </c:choose>
    </div>

    <a class="cart-link" href="<c:url value='/app?action=viewCart'/>" title="Korg">
        🛒
        <c:if test="${cartCount > 0}">
            <span class="badge">${cartCount}</span>
        </c:if>
    </a>
</div>

<div class="page">
    <h2>Produkter</h2>

    <c:choose>
        <c:when test="${empty items}">
            <p style="text-align:center;">Inga produkter hittades.</p>
        </c:when>
        <c:otherwise>
            <div class="grid">
                <c:forEach var="p" items="${items}">
                    <div class="card">
                        <div class="title">${p.name}</div>
                        <div class="desc">${p.description}</div>
                        <div class="price">${p.price} kr</div>
                        <form method="post" action="<c:url value='/app'/>">
                            <input type="hidden" name="action" value="addToCart"/>
                            <input type="hidden" name="itemId" value="${p.id}"/>
                            <input type="hidden" name="qty" value="1"/>
                            <button type="submit" class="btn">Lägg i korg</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
