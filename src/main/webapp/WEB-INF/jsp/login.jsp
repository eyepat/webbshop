<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<html>
<head>
    <meta charset="UTF-8"/>
    <title>Logga in</title>
    <style>
        body { font-family: system-ui, Arial, sans-serif; margin:0; }
        .topbar { display:flex; justify-content:space-between; align-items:center;
            padding:12px 18px; border-bottom:1px solid #e5e5e5; position:sticky; top:0; background:#fff; }
        .page { min-height: calc(100vh - 60px); display:flex; align-items:center; justify-content:center; padding:24px; }
        .card { width: 360px; border:1px solid #e5e5e5; border-radius:14px; box-shadow:0 2px 12px rgba(0,0,0,.06);
            padding:20px 18px; }
        h2 { margin:0 0 14px; text-align:center; }
        .field { display:flex; flex-direction:column; gap:6px; margin:10px 0; }
        .field input { padding:10px 12px; border:1px solid #ccc; border-radius:10px; font-size:15px; }
        .btn { width:100%; padding:10px 12px; border:1px solid #222; background:#fff; border-radius:10px;
            cursor:pointer; font-weight:600; }
        .linkbtn { width:100%; margin-top:10px; border:none; background:transparent; color:#0a58ca;
            cursor:pointer; font-weight:600; }
        .error { color:#c0392b; margin:8px 0 0; text-align:center; }
        #signupPane { display:none; margin-top:8px; border-top:1px dashed #ddd; padding-top:12px; }
    </style>
</head>
<body>

<div class="topbar">
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.authUser}">
                👤 Inloggad som <b>${sessionScope.authUser.username}</b>
                &nbsp;|&nbsp; <a href="<c:url value='/app?action=logout'/>">Logga ut</a>
            </c:when>
            <c:otherwise>
                <a href="<c:url value='/app?action=listItems'/>">← Till produkter</a>
            </c:otherwise>
        </c:choose>
    </div>
    <a href="<c:url value='/app?action=viewCart'/>" title="Korg">🛒</a>
</div>

<div class="page">
    <div class="card">
        <h2>Logga in</h2>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <!-- LOGGA IN -->
        <form method="post" action="<c:url value='/app'/>">
            <input type="hidden" name="action" value="login"/>
            <div class="field">
                <label>👤 Användarnamn</label>
                <input type="text" name="username" required autocomplete="username"/>
            </div>
            <div class="field">
                <label>🔒 Lösenord</label>
                <input type="password" name="password" required minlength="3" autocomplete="current-password"/>
            </div>
            <button type="submit" class="btn">Logga in</button>
        </form>

        <!-- KNAPP SOM VISAR REGISTRERING -->
        <button id="toggleSignup" class="linkbtn" type="button">Skapa konto</button>

        <!-- REGISTRERA (fälls ut) -->
        <div id="signupPane">
            <h2 style="font-size:18px; margin-top:0; text-align:center;">Skapa konto</h2>
            <form method="post" action="<c:url value='/app'/>">
                <input type="hidden" name="action" value="signup"/>
                <div class="field">
                    <label>👤 Användarnamn</label>
                    <input type="text" name="username" required autocomplete="username"/>
                </div>
                <div class="field">
                    <label>🔒 Lösenord</label>
                    <input type="password" name="password" required minlength="3" autocomplete="new-password"/>
                </div>
                <button type="submit" class="btn">Skapa konto</button>
            </form>
        </div>
    </div>
</div>

<script>
    const btn = document.getElementById('toggleSignup');
    const pane = document.getElementById('signupPane');
    btn.addEventListener('click', () => {
        const open = pane.style.display === 'block';
        pane.style.display = open ? 'none' : 'block';
        btn.textContent = open ? 'Skapa konto' : 'Avbryt';
        if (!open) pane.scrollIntoView({behavior:'smooth', block:'center'});
    });
</script>

</body>
</html>
