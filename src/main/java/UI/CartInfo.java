package UI;


import java.util.List;

public class CartInfo {
    private final List<UI.CartLineInfo> lines;
    private final float total;
    private final boolean empty;

    public CartInfo(List<UI.CartLineInfo> lines, float total, boolean empty) {
        this.lines = lines;
        this.total = total;
        this.empty = empty;
    }

    public List<UI.CartLineInfo> getLines() { return lines; }
    public float getTotal() { return total; }
    public boolean isEmpty() { return empty; }
}

