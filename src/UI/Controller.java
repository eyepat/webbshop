package UI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Collection;

import Model.BO.ItemFacade;

@WebServlet("/app")
public class Controller extends HttpServlet {

    private int parseIntOr(HttpServletRequest req, String name, int defVal){
        try { return Integer.parseInt(req.getParameter(name)); }
        catch (Exception e) { return defVal; }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
                resp.sendRedirect(req.getContextPath()+"/app?action=viewCart");
            }
            case "clear" -> {
                ItemFacade.clearCart(req.getSession());
                resp.sendRedirect(req.getContextPath()+"/app?action=viewCart");
            }
            default -> resp.sendError(404, "Unknown action: " + action);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("addToCart".equals(action)) {
            ItemFacade.addToCart(
                    req.getSession(),
                    parseIntOr(req,"itemId",-1),
                    parseIntOr(req,"qty",1)
            );

            String ref = req.getHeader("referer");
            if (ref != null && ref.contains("action=listItems")) {
                resp.sendRedirect(ref);
            } else {
                resp.sendRedirect(req.getContextPath()+"/app?action=listItems");
            }

        } else if ("updateQty".equals(action)) {
            ItemFacade.updateQty(
                    req.getSession(),
                    parseIntOr(req,"itemId",-1),
                    parseIntOr(req,"qty",1)
            );
            resp.sendRedirect(req.getContextPath()+"/app?action=viewCart");

        } else {
            resp.sendError(400, "Unsupported POST action");
        }
    }
}