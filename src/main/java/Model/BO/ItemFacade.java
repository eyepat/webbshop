package Model.BO;

import UI.CartInfo;
import UI.ItemInfo;
import UI.CartLineInfo;
import UI.UserInfo;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ItemFacade {

    // ===== ITEMS =====
    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection<?> c = Item.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Iterator<?> it = c.iterator(); it.hasNext();) {
            Item item = (Item) it.next();
            items.add(new ItemInfo(
                    item.getId(),
                    item.getName(),
                    item.getDescr(),
                    item.getPrice()
            ));
        }
        return items;
    }

    public static ItemInfo getItemById(int id) {
        Item item = Item.getById(id);
        if (item == null) throw new IllegalArgumentException("Item not found: " + id);
        return new ItemInfo(
                item.getId(),
                item.getName(),
                item.getDescr(),
                item.getPrice()
        );
    }

    // ===== CART =====
    private static Cart ensureCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public static void addToCart(HttpSession session, int itemId, int qty) {
        if (qty < 1) qty = 1;
        Cart cart = ensureCart(session);
        Item item = Item.getById(itemId);
        if (item != null) {
            cart.add(item.getId(), item.getName(), item.getPrice(), qty);
        }
    }

    public static void removeFromCart(HttpSession session, int itemId) {
        ensureCart(session).remove(itemId);
    }

    public static void updateQty(HttpSession session, int itemId, int qty) {
        ensureCart(session).updateQty(itemId, qty);
    }

    public static void clearCart(HttpSession session) {
        ensureCart(session).clear();
    }

    public static CartInfo getCartView(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return new CartInfo(List.of(), 0f, true);
        }
        List<CartLineInfo> lines = new ArrayList<>();
        for (Cart.Line l : cart.getLines()) {
            lines.add(new CartLineInfo(
                    l.getItemId(),
                    l.getName(),
                    l.getPrice(),
                    l.getQty(),
                    l.getLineTotal()
            ));
        }
        return new CartInfo(lines, cart.getTotal(), false);
    }

    // ===== USER =====
    public static Optional<UserInfo> login(String username, String password) {
        return User.login(username, password)
                .map(u -> new UserInfo(u.getId(), u.getUsername()));
    }

    public static Optional<UserInfo> signup(String username, String password) {
        return User.signup(username, password)
                .map(u -> new UserInfo(u.getId(), u.getUsername()));
    }
}
