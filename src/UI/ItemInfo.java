package UI;

public class ItemInfo {
    private String name,description;
    private final float price;
    private int id;

    public ItemInfo(int id,String name, String description, float price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public float getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }
}
