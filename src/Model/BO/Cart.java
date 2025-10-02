package Model.BO;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    public static class Line {
        private int itemId;
        private String name;
        private float price;
        private int qty;

        public Line(int itemId, String name, float price, int qty) {
            this.itemId = itemId;
            this.name = name;
            this.price = price;
            this.qty = qty;
        }

        public int getItemId() { return itemId; }
        public String getName() { return name; }
        public float getPrice() { return price; }
        public int getQty() { return qty; }
        public void addQty(int q) { this.qty += q; }
        public void setQty(int q) { this.qty = q; }

        public float getLineTotal() { return price * qty; }
    }

    private final Map<Integer, Line> lines = new LinkedHashMap<>();

    public void add(int itemId, String name, float price, int qty) {
        if (qty < 1) qty = 1;
        Line line = lines.get(itemId);
        if (line == null) {
            lines.put(itemId, new Line(itemId, name, price, qty));
        } else {
            line.addQty(qty);
        }
    }

    public void remove(int itemId) {
        lines.remove(itemId);
    }

    public void updateQty(int itemId, int qty) {
        if (qty <= 0) { lines.remove(itemId); return; }
        Line l = lines.get(itemId);
        if (l != null) l.setQty(qty);
    }


    public void clear() {
        lines.clear();
    }

    public Collection<Line> getLines() { return lines.values(); }

    public float getTotal() {
        float total = 0;
        for (Line line : lines.values()) total += line.getLineTotal();
        return total;
    }

    public boolean isEmpty() { return lines.isEmpty(); }
}
