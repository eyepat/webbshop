package UI;

public class CartLineInfo {
    private final int itemId;
    private final String name;
    private final float price;
    private final int qty;
    private final float lineTotal;

    public CartLineInfo(int itemId, String name, float price, int qty, float lineTotal) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.lineTotal = lineTotal;
    }

    public int getItemId() { return itemId; }
    public String getName() { return name; }
    public float getPrice() { return price; }
    public int getQty() { return qty; }
    public float getLineTotal() { return lineTotal; }
}
