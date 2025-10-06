package UI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import Model.BO.ItemFacade;
import UI.UserInfo; // DTO för inloggad användare

@WebServlet("/app")
public class Controller extends HttpServlet {



    private void setUtf8(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (Exception ignored) {}
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
    }

    private int parseIntOr(HttpServletRequest req, String name, int defVal){
        try { return Integer.parseInt(req.getParameter(name)); }
        catch (Exception e) { return defVal; }
    }

    private void redirect(HttpServletRequest req, HttpServletResponse resp, String path)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setUtf8(req, resp);

        String action = req.getParameter("action");
        if (action == null) action = "listItems";

        switch (action) {
            case "viewCart" -> {
                req.setAttribute("cart", ItemFacade.getCartView(req.getSession()));
                req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
            }
            case "listItems" -> {
                String group = req.getParameter("group");
                Collection<?> items = ItemFacade.getItemsWithGroup(group);
                req.setAttribute("items", items);
                req.getRequestDispatcher("/WEB-INF/jsp/items.jsp").forward(req, resp);
            }
            case "remove" -> {
                ItemFacade.removeFromCart(req.getSession(), parseIntOr(req,"itemId",-1));
                redirect(req, resp,"/app?action=viewCart");
            }
            case "clear" -> {
                ItemFacade.clearCart(req.getSession());
                redirect(req, resp,"/app?action=viewCart");
            }
            case "loginForm" -> {
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }
            case "logout" -> {
                req.getSession().removeAttribute("authUser");
                redirect(req, resp,"/app?action=listItems");
            }

            default -> resp.sendError(404, "Unknown action: " + action);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setUtf8(req, resp);

        String action = req.getParameter("action");
        if (action == null) {
            resp.sendError(400, "Missing action");
            return;
        }

        if ("addToCart".equals(action)) {
            if (req.getSession().getAttribute("authUser") == null) {
                redirect(req, resp,"/app?action=loginForm");
                return;
            }
            ItemFacade.addToCart(
                    req.getSession(),
                    parseIntOr(req,"itemId",-1),
                    parseIntOr(req,"qty",1)
            );


            String ref = req.getHeader("referer");
            if (ref != null && ref.contains(req.getContextPath() + "/app?action=listItems")) {
                resp.sendRedirect(ref);
            } else {
                redirect(req, resp,"/app?action=listItems");
            }

        } else if ("updateQty".equals(action)) {
            ItemFacade.updateQty(
                    req.getSession(),
                    parseIntOr(req,"itemId",-1),
                    parseIntOr(req,"qty",0)
            );
            redirect(req, resp,"/app?action=viewCart");

        } else if ("login".equals(action)) {
            String u = req.getParameter("username");
            String p = req.getParameter("password");

            Optional<UserInfo> user = ItemFacade.login(u, p);
            if (user.isPresent()) {

                req.changeSessionId();
                req.getSession().setAttribute("authUser", user.get());
                redirect(req, resp,"/app?action=listItems");
            } else {
                req.setAttribute("error", "Fel användarnamn eller lösenord.");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }

        } else if ("signup".equals(action)) {
            String u = req.getParameter("username");
            String p = req.getParameter("password");

            Optional<UserInfo> user = ItemFacade.signup(u, p);
            if (user.isPresent()) {
                req.changeSessionId();
                req.getSession().setAttribute("authUser", user.get());
                redirect(req, resp,"/app?action=listItems");
            } else {
                req.setAttribute("error", "Kunde inte skapa konto. Försök igen.");
                req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            }

        } else {
            resp.sendError(400, "Unsupported POST action");
        }
    }
}
